import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IPartnerManager, defaultValue } from 'app/shared/model/partner-manager.model';

interface PEntityState extends EntityState<IPartnerManager> {
  searchField: string;
  searchValue: string;
}

const initialState: PEntityState = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
  searchField: 'name',
  searchValue: '',
};

const apiUrl = 'api/partner-managers';

// Actions

export const getEntities = createAsyncThunk('partnerManager/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?cacheBuster=${new Date().getTime()}`;
  return axios.get<IPartnerManager[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'partnerManager/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IPartnerManager>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'partnerManager/create_entity',
  async (entity: IPartnerManager, thunkAPI) => {
    const result = await axios.post<IPartnerManager>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const selectEntities = createAsyncThunk(
  'partnerManager/select_entities',
  async (data: { searchField: string; searchValue: string }, thunkAPI) => {
    const result = await axios.post<any>('api/select-partner-managers', data);
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'partnerManager/update_entity',
  async (entity: IPartnerManager, thunkAPI) => {
    const result = await axios.put<IPartnerManager>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'partnerManager/partial_update_entity',
  async (entity: IPartnerManager, thunkAPI) => {
    const result = await axios.patch<IPartnerManager>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'partnerManager/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IPartnerManager>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError }
);

// slice

export const PartnerManagerSlice = createEntitySlice({
  name: 'partnerManager',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
          searchField: 'name',
          searchValue: '',
        };
      })
      .addMatcher(isFulfilled(selectEntities), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
          searchField: action.meta.arg.searchField,
          searchValue: action.meta.arg.searchValue,
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, selectEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = PartnerManagerSlice.actions;

// Reducer
export default PartnerManagerSlice.reducer;

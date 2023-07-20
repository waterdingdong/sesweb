package jp.ecn.sesweb.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MasterSearchConditionVm {

    @NotNull
    @Size(min = 1, max = 1000)
    private String searchField;

    @NotNull
    @Size(min = 0, max = 100)
    private String searchValue;

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    // prettier-ignore
	    @Override
	    public String toString() {
	        return "MasterSearchConditionVm{" +
	            "searchField='" + searchField + '\'' +
	            ", searchValue=" + searchValue +
	            '}';
	    }
}

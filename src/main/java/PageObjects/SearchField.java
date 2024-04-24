package PageObjects;

import java.util.List;

/*
This interface specify the operations which are going to perform on search field
on LandingPage.
 */
public interface SearchField {

    public void clickSearchBox();
    public String getPlaceholderText();
    public List<String> getSearchResult();
    public void search(String searchInput);
}

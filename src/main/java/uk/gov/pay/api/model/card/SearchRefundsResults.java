package uk.gov.pay.api.model.card;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.gov.pay.api.model.response.SearchNavigationLinks;
import uk.gov.pay.api.model.search.SearchPagination;

import java.util.List;

@ApiModel(value = "RefundSearchResults")
public class SearchRefundsResults implements SearchPagination {

    @ApiModelProperty(name = "total", example = "100")
    private int total;
    @ApiModelProperty(name = "count", example = "20")
    private int count;
    @ApiModelProperty(name = "page", example = "1")
    private int page;
    @ApiModelProperty(name = "results")
    private List<RefundForSearchRefundsResult> results;
    @ApiModelProperty(name = "_links")
    private SearchNavigationLinks links;

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int getPage() {
        return page;
    }

    public List<RefundForSearchRefundsResult> getResults() {
        return results;
    }

    @Override
    public SearchNavigationLinks getLinks() {
        return links;
    }
}

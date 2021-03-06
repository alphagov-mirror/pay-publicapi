package uk.gov.pay.api.model.search.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import uk.gov.pay.api.model.links.SearchNavigationLinks;
import uk.gov.pay.api.model.search.SearchPagination;

import java.util.List;

@Schema(name = "RefundSearchResults")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SearchRefundsResults implements SearchPagination {

    @Schema(example = "100")
    private int total;
    @Schema(example = "20")
    private int count;
    @Schema(example = "1")
    private int page;
    private List<RefundForSearchRefundsResult> results;
    @JsonProperty("_links")
    private SearchNavigationLinks links;

    public SearchRefundsResults(int total, int count, int page, List<RefundForSearchRefundsResult> results,
                                    SearchNavigationLinks links) {
        this.total = total;
        this.count = count;
        this.page = page;
        this.results = results;
        this.links = links;
    }
    
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

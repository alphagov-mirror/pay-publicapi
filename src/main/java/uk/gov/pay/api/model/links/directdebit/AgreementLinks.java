package uk.gov.pay.api.model.links.directdebit;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.gov.pay.api.model.response.HalLink;
import uk.gov.pay.api.model.response.Link;
import uk.gov.pay.api.model.response.PostLink;

import java.util.List;

import static javax.ws.rs.HttpMethod.GET;

@ApiModel(value = "AgreementLinks", description = "self and next links of an Agreement")
public class AgreementLinks {

    private static final String SELF = "self";
    private static final String NEXT_URL = "next_url";
    private static final String NEXT_URL_POST = "next_url_post";

    @JsonProperty(SELF)
    private Link self;

    @JsonProperty(NEXT_URL)
    private Link nextUrl;

    @JsonProperty(NEXT_URL_POST)
    private PostLink nextUrlPost;

    @ApiModelProperty(value = SELF, dataType = "uk.gov.pay.api.model.response.Link")
    public Link getSelf() {
        return self;
    }

    @ApiModelProperty(value = NEXT_URL, dataType = "uk.gov.pay.api.model.response.Link")
    public Link getNextUrl() {
        return nextUrl;
    }

    @ApiModelProperty(value = NEXT_URL_POST, dataType = "uk.gov.pay.api.model.response.PostLink")
    public PostLink getNextUrlPost() {
        return nextUrlPost;
    }

    public void addKnownLinksValueOf(List<HalLink> chargeLinks) {
        addNextUrlIfPresent(chargeLinks);
        addNextUrlPostIfPresent(chargeLinks);
    }

    private void addNextUrlPostIfPresent(List<HalLink> chargeLinks) {
        chargeLinks.stream()
                .filter(link -> NEXT_URL_POST.equals(link.getRel()))
                .findFirst()
                .ifPresent(chargeLink -> this.nextUrlPost = new PostLink(chargeLink.getHref(), chargeLink.getMethod(), chargeLink.getType(), chargeLink.getParams()));
    }

    private void addNextUrlIfPresent(List<HalLink> chargeLinks) {
        chargeLinks.stream()
                .filter(link -> NEXT_URL.equals(link.getRel()))
                .findFirst()
                .ifPresent(chargeLink -> this.nextUrl = new Link(chargeLink.getHref(), chargeLink.getMethod()));
    }

    public void addSelf(String href) {
        this.self = new Link(href, GET);
    }
}

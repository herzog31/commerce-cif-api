/*******************************************************************************
 *
 *    Copyright 2018 Adobe. All rights reserved.
 *    This file is licensed to you under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License. You may obtain a copy
 *    of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software distributed under
 *    the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR REPRESENTATIONS
 *    OF ANY KIND, either express or implied. See the License for the specific language
 *    governing permissions and limitations under the License.
 *
 ******************************************************************************/

package com.adobe.commerce.cif.api;

import java.util.List;

import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.adobe.commerce.cif.model.cart.Cart;
import com.adobe.commerce.cif.model.cart.ShippingMethod;
import com.adobe.commerce.cif.model.error.ErrorResponse;
import com.adobe.commerce.cif.model.wrapper.AddressWrapper;
import com.adobe.commerce.cif.model.wrapper.PaymentWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/carts")
@Api(value = "/carts")
@Produces(MediaType.APPLICATION_JSON)
public interface CartApi {

    @POST
    @Path("/")
    @ApiOperation(value = "Creates an empty cart. For convenience it also adds a cart entry when product variant id " +
        "and quantity are provided.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to create a cart.", response = ErrorResponse.class)
    })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Cart postCart(
        @ApiParam(value = "Three-digit currency code.", required = true)
        @FormParam("currency") String currency,

        @ApiParam(value = "The product variant id to be added to the cart entry.")
        @FormParam("productVariantId") String productVariantId,

        @ApiParam(value = "The quantity for the product variant.")
        @FormParam("quantity") int quantity,

        @ApiParam(value = "If set, the newly created cart will belong to this customer.")
        @FormParam("customerId") String customerId);

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Returns a cart by ID.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to fetch that cart.", response = ErrorResponse.class)
    })
    Cart getCart(
        @ApiParam(value = "The ID of the cart to be returned.", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @QueryParam("customerId") String customerId);

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Deletes the cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to delete that cart.", response = ErrorResponse.class)
    })
    Cart deleteCart(
        @ApiParam(value = "The ID of the cart to be removed.", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @QueryParam("customerId") String customerId);

    @POST
    @Path("/{id}/entries")
    @ApiOperation(value = "Adds a new cart entry to an existing cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to add an entry to that cart.", response = ErrorResponse.class)
    })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Cart postCartEntry(
        @ApiParam(value = "The ID of the cart for the new entry", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "The product variant id to be added to the cart entry. If product variant exists in the" +
            " cart then the cart entry quantity is increased with the provided quantity", required = true)
        @FormParam("productVariantId") String productVariantId,

        @ApiParam(value = "The quantity for the new entry.", required = true)
        @FormParam("quantity")
        @Min(value = 0) int quantity,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @FormParam("customerId") String customerId);

    @PUT
    @Path("/{id}/entries/{cartEntryId}")
    @ApiOperation(value = "Updates an existing cart entry.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Resource not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to update that cart entry.", response = ErrorResponse.class)
    })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Cart putCartEntry(
        @ApiParam(value = "The ID of the cart.", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "The cart entry id to be updated.", required = true)
        @PathParam("cartEntryId") String cartEntryId,

        @ApiParam(value = "The cart entry quantity. When quantity is 0 the entry will be removed otherwise the " +
            "value is used as new quantity.", required = true)
        @FormParam("quantity")
        @Min(value = 0) int quantity,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @FormParam("customerId") String customerId);

    @DELETE
    @Path("/{id}/entries/{cartEntryId}")
    @ApiOperation(value = "Removes a cart entry from the cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Resource not found - cart or entry.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to delete that cart entry.", response = ErrorResponse.class)
    })
    Cart deleteCartEntry(
        @ApiParam(value = "The ID of the cart.", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "The cart entry id to be removed.", required = true)
        @PathParam("cartEntryId") String cartEntryId,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @QueryParam("customerId") String customerId);

    @POST
    @Path("/{id}/shippingaddress")
    @ApiOperation(value = "Sets the shipping address for the cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to set the shipping address", response = ErrorResponse.class)
    })
    @Consumes(MediaType.APPLICATION_JSON)
    Cart postShippingAddress(
        @ApiParam(value = "The ID of the cart for the shipping address", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "The shipping address and an optional customer id that must be set if the cart belongs to a customer.", required = true)
        AddressWrapper addressWrapper);

    @DELETE
    @Path("/{id}/shippingaddress")
    @ApiOperation(value = "Deletes the shipping address for the cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to delete the shipping address", response = ErrorResponse.class)
    })
    Cart deleteShippingAddress(
        @ApiParam(value = "The ID of the cart for which the shipping address will be deleted.", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @QueryParam("customerId") String customerId);

    @POST
    @Path("/{id}/billingaddress")
    @ApiOperation(value = "Sets the billing address for the cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to set the billing address", response = ErrorResponse.class)
    })
    @Consumes(MediaType.APPLICATION_JSON)
    Cart postBillingAddress(
        @ApiParam(value = "The ID of the cart for the billing address", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "The billing address and an optional customer id that must be set if the cart belongs to a customer.", required = true)
        AddressWrapper addressWrapper);

    @DELETE
    @Path("/{id}/billingaddress")
    @ApiOperation(value = "Deletes the billing address for the cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to delete the billing address", response = ErrorResponse.class)
    })
    Cart deleteBillingAddress(
        @ApiParam(value = "The ID of the cart for which the billing address will be deleted.", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @QueryParam("customerId") String customerId);

    @POST
    @Path("/{id}/shippingmethod")
    @ApiOperation(value = "Updates the shipping method for the cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to set the shipping method.", response = ErrorResponse.class)
    })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Cart postShippingMethod(
        @ApiParam(value = "The ID of the cart for which the shipping method will be updated.", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "The shipping method id.", required = true)
        @FormParam("shippingMethodId") String shippingMethodId,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @FormParam("customerId") String customerId);

    @DELETE
    @Path("/{id}/shippingmethod")
    @ApiOperation(value = "Deletes the shipping method for the cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to delete the shipping method.", response = ErrorResponse.class)
    })
    Cart deleteShippingMethod(
        @ApiParam(value = "The ID of the cart for which the shipping method will be deleted.", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @QueryParam("customerId") String customerId);

    @GET
    @Path("/{id}/shippingmethods")
    @ApiOperation(value = "Retrieves the available shipping methods for the current cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class)
    })
    List<ShippingMethod> getShippingMethods(
        @ApiParam(value = "The id of the cart", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @QueryParam("customerId") String customerId);

    @POST
    @Path("/{id}/payment")
    @ApiOperation(value = "Creates a payment for this shopping cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to set the payment.", response = ErrorResponse.class)
    })
    @Consumes(MediaType.APPLICATION_JSON)
    Cart postPayment(
        @ApiParam(value = "The ID of the cart for which the payment will be set.", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "The payment to create. If the cart belongs to a customer, the customer id must be set.", required = true)
        PaymentWrapper paymentWrapper);

    @DELETE
    @Path("/{id}/payment")
    @ApiOperation(value = "Removes the payment from the shopping cart. ")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Cart not found.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Not allowed to update the payment.", response = ErrorResponse.class)
    })
    Cart deletePayment(
        @ApiParam(value = "The ID of the cart for which the payment will be deleted.", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @QueryParam("customerId") String customerId);

    @POST
    @Path("/{id}/coupons")
    @ApiOperation(value = "Adds a coupon to the shopping cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found.", response = ErrorResponse.class)
    })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Cart postCoupons(
        @ApiParam(value = "The id of the cart to which the coupon will be added.", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "The coupon code.", required = true)
        @FormParam("code") String code,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @FormParam("customerId") String customerId
    );

    @DELETE
    @Path("/{id}/coupons/{couponId}")
    @ApiOperation(value = "Deletes a coupon from the shopping cart.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found.", response = ErrorResponse.class)
    })
    Cart deleteCoupons(
        @ApiParam(value = "The id of the cart for which the coupon will be deleted.", required = true)
        @PathParam("id") String id,

        @ApiParam(value = "The id of the coupon that will be deleted.", required = true)
        @PathParam("couponId") String couponId,

        @ApiParam(value = "If the cart belongs to a customer, this must be set to the customer id owning that cart.")
        @QueryParam("customerId") String customerId
    );

}

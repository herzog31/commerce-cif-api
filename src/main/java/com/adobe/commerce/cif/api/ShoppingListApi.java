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

import com.adobe.commerce.cif.model.common.PagedResponse;
import com.adobe.commerce.cif.model.error.ErrorResponse;
import com.adobe.commerce.cif.model.shoppinglist.ShoppingList;
import com.adobe.commerce.cif.model.shoppinglist.ShoppingListEntry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

import javax.validation.constraints.Min;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/shoppinglists")
@Api(value = "/shoppinglists")
@Produces(MediaType.APPLICATION_JSON)
public interface ShoppingListApi {

    @GET
    @Path("/")
    @ApiOperation(value = "Gets a users shopping lists.",
                  notes = "The entries property is empty for all shopping lists in the response. To retrieve entries, query a single shopping list.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found.", response = ErrorResponse.class)
    })
    PagedResponse<ShoppingList> getShoppingLists(
        @ApiParam(value = "Defines the number of shopping lists to skip.")
        @QueryParam(value = "offset")
        @Min(value = 0)
        Integer offset,

        @ApiParam(value = "Defines the maximum number of shopping lists to be returned.")
        @QueryParam(value = "limit")
        @Min(value = 0)
        Integer limit
    );

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Gets a users shopping list with a given id.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found.", response = ErrorResponse.class)
    })
    ShoppingList getShoppingList(
        @ApiParam(value = "The id of the shopping list to return.", required = true)
        @PathParam("id")
        String id
    );

    @POST
    @Path("/")
    @ApiOperation(value = "Creates a new shopping list for the current user.",
                  notes = "The name and description properties will be stored as localized strings in the language defined by the Content-Language header.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created.", response = ShoppingList.class,
                     responseHeaders = @ResponseHeader(name = "Location", description = "Location of the newly created shopping list.", response = String.class)),
        @ApiResponse(code = 400, message = "Bad Request.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found.", response = ErrorResponse.class)
    })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    ShoppingList postShoppingList(
        @ApiParam(value = "Name of the shopping list.", required = true)
        @FormParam("name")
        String name,

        @ApiParam(value = "Description of the shopping list.")
        @FormParam("description")
        String description,

        @ApiParam(value = "Language of the shopping list.")
        @HeaderParam("Content-Language")
        String contentLanguage
    );

    @PUT
    @Path("/{id}")
    @ApiOperation(value = "Replaces a shopping list with the given one.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found.", response = ErrorResponse.class)
    })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    ShoppingList putShoppingList(
        @ApiParam(value = "The id of the shopping list to replace.", required = true)
        @PathParam("id")
        String id,

        @ApiParam(value = "Name of the shopping list.", required = true)
        @FormParam("name")
        String name,

        @ApiParam(value = "Description of the shopping list.")
        @FormParam("description")
        String description
    );

    @DELETE
    @Path("/{id}")
    @ApiOperation(value = "Deletes a shopping list.")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "No Content."),
        @ApiResponse(code = 400, message = "Bad Request.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found.", response = ErrorResponse.class)
    })
    void deleteShoppingList(
        @ApiParam(value = "The id of the shopping list to be deleted.", required = true)
        @PathParam("id")
        String id
    );

    @GET
    @Path("/{id}/entries")
    @ApiOperation(value = "Gets all entries from a shopping list.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found.", response = ErrorResponse.class)
    })
    PagedResponse<ShoppingListEntry> getShoppingListEntries(
        @ApiParam(value = "The id of the shopping list to return entries from.", required = true)
        @PathParam("id")
        String id,

        @ApiParam(value = "Defines the number of entries to skip.")
        @QueryParam(value = "offset")
        @Min(value = 0)
        Integer offset,

        @ApiParam(value = "Defines the maximum number of entries to be returned.")
        @QueryParam(value = "limit")
        @Min(value = 0)
        Integer limit
    );

    @GET
    @Path("/{id}/entries/{entryId}")
    @ApiOperation(value = "Gets a single entry from a shopping list.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found.", response = ErrorResponse.class)
    })
    ShoppingListEntry getShoppingListEntry(
        @ApiParam(value = "The id of the shopping list.", required = true)
        @PathParam("id")
        String id,

        @ApiParam(value = "The id of the shopping list entry to return.", required = true)
        @PathParam("entryId")
        String entryId
    );

    @POST
    @Path("/{id}/entries")
    @ApiOperation(value = "Creates a new entry for a shopping list.")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Created.", response = ShoppingList.class,
                     responseHeaders = @ResponseHeader(name = "Location", description = "Location of the newly created entry.", response = String.class)),
        @ApiResponse(code = 400, message = "Bad Request.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found.", response = ErrorResponse.class)
    })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    ShoppingListEntry postShoppingListEntry(
        @ApiParam(value = "The id of the shopping list.", required = true)
        @PathParam("id")
        String id,

        @ApiParam(value = "The quantity for the new entry.", required = true)
        @FormParam("quantity")
        @Min(value = 0)
        int quantity,

        @ApiParam(value = "The product variant id to be added to the entry. If the product variant exists in the shopping list, its quantity is increased with the provided quantity.", required = true)
        @FormParam("productVariantId")
        String productVariantId
    );

    @PUT
    @Path("/{id}/entries/{entryId}")
    @ApiOperation(value = "Replaces an entry with the given one.")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Bad Request.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found.", response = ErrorResponse.class)
    })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    ShoppingListEntry putShoppingListEntry(
        @ApiParam(value = "The id of the shopping list.", required = true)
        @PathParam("id")
        String id,

        @ApiParam(value = "The id of the entry to replace.", required = true)
        @PathParam("entryId")
        String entryId,

        @ApiParam(value = "The quantity for the new entry.", required = true)
        @FormParam("quantity")
        @Min(value = 0)
        int quantity,

        @ApiParam(value = "The product variant id to be added to the entry. If the product variant exists in another entry in the shopping list, this request fails.", required = true)
        @FormParam("productVariantId")
        String productVariantId
    );

    @DELETE
    @Path("/{id}/entries/{entryId}")
    @ApiOperation(value = "Deletes an entry from a shopping list.")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "No Content."),
        @ApiResponse(code = 400, message = "Bad Request.", response = ErrorResponse.class),
        @ApiResponse(code = 403, message = "Forbidden.", response = ErrorResponse.class),
        @ApiResponse(code = 404, message = "Not Found.", response = ErrorResponse.class)
    })
    void deleteShoppingListEntry(
        @ApiParam(value = "The id of the shopping list.", required = true)
        @PathParam("id")
        String id,

        @ApiParam(value = "The id of the entry.", required = true)
        @PathParam("entryId")
        String entryId
    );

}

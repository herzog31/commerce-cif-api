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

package com.adobe.commerce.cif.model.common;

import java.util.Date;

import com.adobe.commerce.cif.model.customer.Customer;
import io.swagger.annotations.ApiModelProperty;

public class Payment {

    @ApiModelProperty(value = "The id of the payment.")
    protected String id;

    @ApiModelProperty(value = "The customer the payment belongs to. If this is not set the payment belongs to an " +
            "anonymous customer.")
    protected Customer customer;

    @ApiModelProperty(value = "The method for this payment like Card or Cash.", required = true)
    protected String method;

    @ApiModelProperty(value = "The amount of the payment.")
    protected Price amount;

    @ApiModelProperty(value = "The token used to communicate with the payment service provider.")
    protected String token;

    @ApiModelProperty(value = "The external status code for the payment.")
    protected String statusCode;

    @ApiModelProperty(value = "The external status message/text for the payment.")
    protected String status;

    @ApiModelProperty(value = "The date when this payment was created.")
    protected Date createdDate;

    @ApiModelProperty(value = "The date when this payment was last modified.")
    protected Date lastModifiedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Price getAmount() {
        return amount;
    }

    public void setAmount(Price amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}

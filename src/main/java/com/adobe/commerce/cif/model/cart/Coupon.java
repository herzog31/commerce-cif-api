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

package com.adobe.commerce.cif.model.cart;

import com.adobe.commerce.cif.model.common.LocalizedString;
import io.swagger.annotations.ApiModelProperty;

public class Coupon {

    @ApiModelProperty(value = "The id of the coupon instance applied to a cart. If not supported by commerce engine, should be equal to code.", required = true)
    protected String id;

    @ApiModelProperty(value = "The code to activate the coupon.", required = true)
    protected String code;

    @ApiModelProperty(value = "The description of the coupon.")
    protected LocalizedString description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalizedString getDescription() {
        return description;
    }

    public void setDescription(LocalizedString description) {
        this.description = description;
    }
}

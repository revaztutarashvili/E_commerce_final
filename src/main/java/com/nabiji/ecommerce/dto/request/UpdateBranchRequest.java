// 📂 src/main/java/com/nabiji/ecommerce/dto/request/UpdateBranchRequest.java
package com.nabiji.ecommerce.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBranchRequest {

    private String name;
    private String address;
    private Boolean active;
}
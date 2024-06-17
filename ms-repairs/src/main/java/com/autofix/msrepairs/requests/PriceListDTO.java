package com.autofix.msrepairs.requests;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceListDTO {
    private Long id;
    private String repairType;
    private double gasolinePrice;
    private double dieselPrice;
    private double hybridPrice;
    private double electricPrice;
}

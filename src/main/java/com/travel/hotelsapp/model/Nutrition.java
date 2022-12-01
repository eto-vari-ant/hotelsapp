package com.travel.hotelsapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum Nutrition {
    FB("FB - Завтраки, обеды и ужины"),
    HB("HB - Завтраки и ужины"),
    BB("BB - Завтрак по системе «шведский стол»"),
    AI("AI - Завтраки, обеды, ужины + напитки"),
    UAI("UAI - Завтраки, обеды, ужины + напитки (расширенное меню)"),
    RO("RO - Без питания"),
    FBPLUS("FB+ - Завтраки, обеды и ужины (расширенное меню)"),
    HBPLUS("HB+ - Завтраки и ужины (расширенное меню)"),
    SC("SC - Самообслуживание");
    private String type;

    public String getType(){
        return type;
    }
}

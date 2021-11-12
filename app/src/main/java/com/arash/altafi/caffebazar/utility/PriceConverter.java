package com.arash.altafi.caffebazar.utility;

import java.text.DecimalFormat;

public class PriceConverter {

    public static String priceConvert(int price)
    {
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        String prices = decimalFormat.format(Integer.valueOf(price));
        return prices + " " + "تومان";
    }

}

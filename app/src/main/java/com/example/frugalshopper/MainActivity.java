package com.example.frugalshopper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Variables for Product A
    private double getPriceA = 0.0;
    private double getPoundsA = 0;
    private double getOuncesA = 0;
    private double productAPrice = 0.0;

    // Variables for Product B
    private double getPriceB = 0.0;
    private double getPoundsB = 0;
    private double getOuncesB = 0;
    private double productBPrice = 0.0;

    // Variables for Product C
    private double getPriceC = 0.0;
    private double getPoundsC = 0;
    private double getOuncesC = 0;
    private double productCPrice = 0.0;

    private TextView inputPriceA;
    private TextView inputPoundsA;
    private TextView inputOuncesA;

    private TextView inputPriceB;
    private TextView inputPoundsB;
    private TextView inputOuncesB;

    private TextView inputPriceC;
    private TextView inputPoundsC;
    private TextView inputOuncesC;

    private TextView finalBuyOutput;

    // Toast Messages
    private final String numberFormatErrorMessage = "Please Enter Data In Right Format";
    private final String exceptionErrorMessage = "Something Went Wrong";
    private final String tryAgainErrorMessage = "Please try again";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button compareFrugalBuy = findViewById(R.id.compareFrugalBuy);
        finalBuyOutput = findViewById(R.id.finalBuyOutput);

        inputPriceA = findViewById(R.id.inputPriceA);
        inputPoundsA = findViewById(R.id.inputPoundsA);
        inputOuncesA = findViewById(R.id.inputOuncesA);

        inputPriceB = findViewById(R.id.inputPriceB);
        inputPoundsB = findViewById(R.id.inputPoundsB);
        inputOuncesB = findViewById(R.id.inputOuncesB);

        inputPriceC = findViewById(R.id.inputPriceC);
        inputPoundsC = findViewById(R.id.inputPoundsC);
        inputOuncesC = findViewById(R.id.inputOuncesC);

        compareFrugalBuy.setOnClickListener((view) -> {
            finalBuyOutput.setText(" ");
            try {
                if(inputPriceA.getText() != null && inputPoundsA.getText() != null && inputOuncesA.getText() != null) {
                    getPriceA = ParseDouble(inputPriceA.getText().toString());
                    getPoundsA = (int) ParseInt(inputPoundsA.getText().toString());
                    getOuncesA = (int) ParseInt(inputOuncesA.getText().toString());
                    try {
                        productAPrice = getPriceA / ((getPoundsA * 16) + getOuncesA);
                        if (Double.isNaN(productAPrice) || Double.isInfinite(productAPrice)) {
                            productAPrice = 0.0;
                        }
                    } catch(ArithmeticException error){
                        productAPrice = 0.0;
                    }
                }

                if(inputPriceB.getText() != null && inputPoundsB.getText() != null && inputOuncesB.getText() != null) {
                    getPriceB = ParseDouble(inputPriceB.getText().toString());
                    getPoundsB = (int) ParseInt(inputPoundsB.getText().toString());
                    getOuncesB = (int) ParseInt(inputOuncesB.getText().toString());
                    try {
                        productBPrice = getPriceB / ((getPoundsB * 16) + getOuncesB);
                        if (Double.isNaN(productBPrice) || Double.isInfinite(productBPrice)) {
                            productBPrice = 0.0;
                        }
                    } catch(ArithmeticException error){
                        productBPrice = 0.0;
                    }
                }

                if(inputPriceC.getText() != null && inputPoundsC.getText() != null && inputOuncesC.getText() != null) {
                    getPriceC = ParseDouble(inputPriceC.getText().toString());
                    getPoundsC = (int) ParseInt(inputPoundsC.getText().toString());
                    getOuncesC = (int) ParseInt(inputOuncesC.getText().toString());
                    try {
                        productCPrice = getPriceC / ((getPoundsC * 16) + getOuncesC);
                        if (Double.isNaN(productCPrice) || Double.isInfinite(productCPrice)) {
                            productCPrice = 0.0;
                        }
                    } catch(ArithmeticException error){
                        productCPrice = 0.0;
                    }
                }

                if ((getPoundsA > 0 || getOuncesA > 0) && getPriceA == 0.0) {
                    showError("price");
                    return;
                }

                if ((getPoundsB > 0 || getOuncesB > 0) && getPriceB == 0.0) {
                    showError("price");
                    return;
                }

                if ((getPoundsC > 0 || getOuncesC > 0) && getPriceC == 0.0) {
                    showError("price");
                    return;
                }

                if ((getPoundsA == 0 && getOuncesA == 0) && getPriceA > 0.0) {
                    showError("atleast one weight");
                    return;
                }

                if ((getPoundsB == 0 && getOuncesB == 0) && getPriceB > 0.0) {
                    showError("atleast one weight");
                    return;
                }

                if ((getPoundsC == 0 && getOuncesC == 0) && getPriceC > 0.0) {
                    showError("atleast one weight");
                    return;
                }

                finalBuyOutput.setText(computeFrugalBuy(productAPrice, productBPrice, productCPrice));
            } catch (NumberFormatException error) {
                inputPriceA.setText(null);
                inputPriceB.setText(null);
                inputPriceB.setText(null);

                inputPoundsA.setText(null);
                inputPoundsB.setText(null);
                inputPoundsC.setText(null);

                inputOuncesA.setText(null);
                inputOuncesB.setText(null);
                inputOuncesC.setText(null);

                Toast toast = Toast.makeText(getApplicationContext(), numberFormatErrorMessage, Toast.LENGTH_SHORT);
                toast.show();
            } catch (Exception error) {
                inputPriceA.setText(null);
                inputPriceB.setText(null);
                inputPriceB.setText(null);

                inputPoundsA.setText(null);
                inputPoundsB.setText(null);
                inputPoundsC.setText(null);

                inputOuncesA.setText(null);
                inputOuncesB.setText(null);
                inputOuncesC.setText(null);

                Toast toast = Toast.makeText(getApplicationContext(), exceptionErrorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    /**
     * Displays an error message in a Toast.
     * @param input - The type of input for which an error occurred.
     */
    private void showError(String input) {
        Toast toast = Toast.makeText(getApplicationContext(), "Please enter " + input, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Parses a string representation of a double into a double value.
     * @param number - The string representation of the double.
     * @return The parsed double value or -1 if parsing fails.
     */
    private double ParseDouble(String number) {
        if (number != null && number.length() > 0) {
            try {
                return Double.parseDouble(number);
            } catch (Exception error) {
                return -1;
            }
        } else return 0;
    }

    /**
     * Parses a string representation of an integer into an integer value.
     * @param number - The string representation of the integer.
     * @return The parsed integer value or -1 if parsing fails.
     */
    private double ParseInt(String number) {
        if (number != null && number.length() > 0) {
            try {
                return Integer.parseInt(number);
            } catch (Exception error) {
                return -1;
            }
        } else return 0;
    }

    /**
     * Computes the frugal buy based on the prices of three products.
     * @param productAPrice - The price per unit of Product A.
     * @param productBPrice - The price per unit of Product B.
     * @param productCPrice - The price per unit of Product C.
     * @return A message indicating the product to buy and its price, or an error message.
     */
    private String computeFrugalBuy(double productAPrice, double productBPrice, double productCPrice) {
        String outputMessage;
        Double[] productPrices = {productAPrice, productBPrice, productCPrice};
        double minValue = Double.MAX_VALUE;
        List<String> productsToBuy = new ArrayList<>();

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);

        for (int i = 0; i < productPrices.length; i++) {
            double roundedPrice = Double.parseDouble(decimalFormat.format(productPrices[i]));

            if (minValue > roundedPrice && roundedPrice != 0.0) {
                minValue = roundedPrice;
                productsToBuy.clear();
                productsToBuy.add(getProductName(i));
            } else if (minValue == roundedPrice && roundedPrice != 0.0) {
                productsToBuy.add(getProductName(i));
            }
        }

        if (!productsToBuy.isEmpty()) {
            outputMessage = concatenateProductNames(productsToBuy) + " at $" + decimalFormat.format(minValue);
        } else {
            outputMessage = " ";
            Toast toast = Toast.makeText(getApplicationContext(), tryAgainErrorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

        return outputMessage;
    }

    /**
     * Gets the name of a product based on its index.
     * @param index - The index of the product.
     * @return The name of the product (A, B, C).
     */
    private String getProductName(int index) {
        switch (index) {
            case 0:
                return "A";
            case 1:
                return "B";
            case 2:
                return "C";
            default:
                return "";
        }
    }

    /**
     * Concatenates a list of product names into a comma-separated string.
     * @param productNames - List of product names.
     * @return A comma-separated string of product names.
     */
    private String concatenateProductNames(List<String> productNames) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < productNames.size(); i++) {
            result.append(productNames.get(i));
            if (i < productNames.size() - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }
}
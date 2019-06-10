package com.mindvalley.utility;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

/**
 * This class is used to perform date and time related operations.
 *
 * @author SandeepD
 */

public class DateTimeUtil
{
    /**
     * This function is used to convert the String formatted date into Calendar instance.
     *
     * @param inputFormat - The Input Date format
     * @param inputDate   -The input Date in String
     * @return - The Calendar instance
     */
    public static Date getDateFromString(String inputFormat, String inputDate)
    {

        Date parsed = null;

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());

        try
        {
            parsed = df_input.parse(inputDate);
        } catch (Exception e)
        {
            Timber.e("formattedDateFromString", "Exception in formattedDateFromString(): " + e.getMessage());
        }
        return parsed;

    }

    /**
     * This function is used to convert the Calendar date to output Format.
     *
     * @param outputFormat - The desired ouput format of the date
     * @param inputDate    - The Date in Calendar Object
     * @return - The formatted date
     */
    public static String formattedDateFromDate(Date inputDate, String outputFormat)
    {

        String outputDate = "";

        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try
        {
            outputDate = df_output.format(inputDate);
        } catch (Exception e)
        {
            Timber.e("formattedDateFromCal", "Exception in formattedDateFromCalendar(): " + e.getMessage());
        }
        return outputDate;

    }
}

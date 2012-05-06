package org.mad.app.hokiehelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Dining_Handler
{
    private HashMap<String, Integer> halls;

    public static final String[] allHalls = { "d2", "deets", "dx",
        "hokie_grill", "owens", "shultz", "shultz_express", "west_end", "abp_glc", "abp_cafe", "abp_kiosk", "sbarro" };
    public static final int CLOSED = 0;
    public static final int OPEN = 1;
    public static final int OPEN_EX = 2;
    public static final int CLOSED_EX = 3;

    public Dining_Handler()
    {
        halls = new HashMap<String, Integer>();
        resetMap();
    }

    public void resetMap()
    {
        halls.put("d2", CLOSED);
        halls.put("deets", CLOSED);
        halls.put("dx", CLOSED);
        halls.put("hokie_grill", CLOSED);
        halls.put("owens", CLOSED);
        halls.put("shultz", CLOSED);
        halls.put("shultz_express", CLOSED);
        halls.put("west_end", CLOSED);
        halls.put("abp_glc", CLOSED);
        halls.put("abp_cafe", CLOSED);
        halls.put("abp_kiosk", CLOSED);
        halls.put("sbarro", CLOSED);
    }

    public ArrayList<String> getHallsForState(String state)
    {
        ArrayList<String> open = new ArrayList<String>();
        ArrayList<String> openEx = new ArrayList<String>();
        ArrayList<String> closed = new ArrayList<String>();
        ArrayList<String> closedEx = new ArrayList<String>();

        for (int i = 0; i < allHalls.length; i++)
        {
            refreshOpen(allHalls[i]);
            if (halls.get(allHalls[i]) == CLOSED)
                closed.add(allHalls[i]);
            else if (halls.get(allHalls[i]) == OPEN)
                open.add(allHalls[i]);
            else if (halls.get(allHalls[i]) == OPEN_EX)
                openEx.add(allHalls[i] + "_ex");
            else if (halls.get(allHalls[i]) == CLOSED_EX)
                closedEx.add(allHalls[i] + "_ex");
        }

        if (state.equals("open"))
        {
            for (int i = 0; i < open.size(); i++)
                openEx.add(open.get(i));
            return openEx;
        }
        else if (state.equals("closed"))
        {
            for (int i = 0; i < closed.size(); i++)
                closedEx.add(closed.get(i));
            return closedEx;
        }
        return null;
    }

    public int getState(int start, int end, int current, int minutes, boolean flag)
    {
        if (minutes < 0)
        {
            if (current + 1 == end)
                return OPEN_EX;
            else if (current + 1 == start)
                return CLOSED_EX;
            else if (current >= start && current <= end)
                return OPEN;
            else
                return CLOSED;
        }
        else
        {
            if (flag)
            {
                if ((current == start && minutes < 30) ||
                        (current + 1 == start && minutes > 30))
                    return CLOSED_EX;
                else if (current + 1 == end)
                    return OPEN_EX;
                else if ((current == start && minutes > 30) || 
                        (current > start && current <= end))
                    return OPEN;
                else
                    return CLOSED;
            }
            else
            {
                if (current + 1 == start)
                    return CLOSED_EX;
                else if ((current == end && minutes < 30) ||
                        current + 1 == end && minutes > 30)
                    return OPEN_EX;
                else if ((current == end && minutes < 30) ||
                        current >= start && current < end)
                    return OPEN;
                else
                    return CLOSED;
            }
        }
    }

    public int refreshOpen(String hall)
    {
        Date date = new Date(System.currentTimeMillis());
        String[] str = date.toString().split("\\s+"); 
        String[] time = str[3].split(":");
        int current = Integer.parseInt(time[0]);
        int minutes = Integer.parseInt(time[1]);
        int returnValue = CLOSED;
        if (hall.equals("d2"))
        {
            if (str[0].equals("Sun"))
                returnValue = getState(11, 19, current, -1, false);
            else if (str[0].charAt(0) != 'S')
            {
                returnValue = getState(7, 9, current, minutes, false);
                if (returnValue == CLOSED)
                {
                    returnValue = getState(11, 14, current, -1, false);
                    if (returnValue == CLOSED)
                        returnValue = getState(17, 19, current, -1, false);
                }
            }
        }
        else if (hall.equals("deets"))
        {
            if (str[0].charAt(0) == 'S')
                returnValue = getState(10, 24, current, -1, false);
            else
                returnValue = getState(7, 24, current, minutes, true);
        }
        else if (hall.equals("dx"))
        {
            if (str[0].charAt(0) == 'S')
            {
                returnValue = getState(9, 24, current, -1, false);
                if (returnValue == OPEN_EX)
                    returnValue = OPEN;
                else if (returnValue == CLOSED)
                    returnValue = getState(0, 2, current, -1, false);
            }
            else
            {
                returnValue = getState(7, 24, current, -1, false);
                if (returnValue == OPEN_EX)
                    returnValue = OPEN;
                else if (returnValue == CLOSED)
                    returnValue = getState(0, 2, current, -1, false);
            }
        }
        else if (hall.equals("hokie_grill"))
        {
            if (str[0].charAt(0) != 'S')
                returnValue = getState(10, 21, current, minutes, true);
            else if (str[0].equals("Sat"))
                returnValue = getState(12, 20, current, -1, false);
        }
        else if (hall.equals("owens"))
        {
            returnValue = getState(10, 20, current, minutes, true);
        }
        else if (hall.equals("shultz"))
        {
            returnValue = CLOSED;
        }
        else if (hall.equals("shultz_express"))
        {
            if (str[0].charAt(0) == 'F')
                returnValue = getState(7, 16, current, -1, false);
            else if (str[0].charAt(0) != 'S')
                returnValue = getState(7, 14, current, -1, false);
        }
        else if (hall.equals("west_end"))
        {
            if (str[0].equals("Sun"))
                returnValue = getState(11, 20, current, -1, false);
            else if (str[0].equals("Sat"))
                returnValue = getState(11, 19, current, -1, false);
            else
                returnValue = getState(10, 20, current, minutes, true);
        }
        else if (hall.equals("abp_glc"))
        {
            if (!str[0].equals("S"))
                returnValue = getState(7, 22, current, minutes, true);
        }
        else if (hall.equals("abp_cafe"))
        {
            if (!str[0].equals("S") && !(str[0].charAt(0) == 'F'))
                returnValue = getState(8, 19, current, minutes, true);
            else if (str[0].charAt(0) == 'F') {
                returnValue = getState(9, 15, current, -1, false);
            }
            else if (str[0].equals("Sat")) {
                returnValue = getState(10, 21, current, -1, false);
            } else {
                returnValue = getState(11, 19, current, -1, false);
            }
        }
        else if (hall.equals("abp_kiosk"))
        {
            if (!str[0].equals("S"))
                returnValue = getState(7, 21, current, minutes, true);
        }
        else {
            if (!str[0].equals("S"))
                returnValue = getState(10, 21, current, minutes, true);
            else if (str[0].equals("Sat")) {
                returnValue = getState(11, 19, current, -1, false);
            } else {
                returnValue = getState(12, 19, current, -1, false);
            }
        }
        halls.put(hall, returnValue);
        return returnValue;
    }
}

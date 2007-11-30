/*
 * Copyright 2007 Sam Hough
 * 
 * This file is part of REDROBOT.
 *
 * REDROBOT is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * REDROBOT is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with REDROBOT.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.redspr.redrobot;

import java.net.URL;
import java.util.Stack;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;

public class SeleniumRobot implements Robot {
    private Stack<String> history = new Stack<String>();

    private Selenium sel;

    private String browserString;

    // private String base = "http://localhost:8080";

    public SeleniumRobot() {
        this("*firefox");
    }

    private Selenium getSelenium(URL url) {
        // TODO 00 only do this if proto/domain/port changed
        if (sel == null) {
            String x = url.getProtocol() + "://" + url.getHost() + ":"
                    + url.getPort();

            sel = new DefaultSelenium("localhost", 4444, browserString, x);
            sel.start();
        }
        return sel;
    }

    public SeleniumRobot(String browserString2) {
        this.browserString = browserString2;
        // TODO 00 need to be able to configure this from outside (bean style)

    }

    public void back() {
        sel.open(history.pop());
        // sel.waitForPageToLoad("10000");
    }

    public void click(String... x) {
        history.push(sel.getLocation()); // TODO 00 may not be a page
                                            // transition!? (not if same URL?)
        sel.click(loc(x));
        try {
            sel.waitForPageToLoad("10000");
        } catch (Throwable t) {
        }
        ;
    }

    public int findText(String x) {
        if (sel.isElementPresent("xpath=//node()[text()='" + x + "']")) {
            return 1;
        } else {
            return 0;
        }
    }

    public String get(String... x) {
        try {
            return sel.getSelectedLabel(loc(x));
        } catch (SeleniumException ex) {
            // not a select thing
            // XXX want nicer way
        }
        return sel.getValue(locKey(x)).replaceAll("\\r", "");
    }

    public String getConfirmation() {
        return sel.getConfirmation();
    }

    public boolean isChecked(String... x) {
        return sel.isChecked(locCheckable(x));
    }

    private String loc(String... x) {
        return "fuzzy=" + x[0];
    }

    private String locKey(String... x) {
        return "fuzzyKey=" + x[0];
    }

    private String locCheckable(String... x) {
        return "fuzzyCheckable=" + x[0];
    }

    public void open(URL url) {
        getSelenium(url);
        sel.open(url.getPath());
        sel.waitForPageToLoad("10000");
    }

    public void selenium(String x) {
        sel.fireEvent(x, "mousedown");
        sel.fireEvent(x, "mouseup");
    }

    public void set(String... x) {
        String[] n = new String[x.length - 1];
        for (int i = 0; i < n.length; i++) {
            n[i] = x[i];
        }
        String v = x[x.length - 1];
        try {
            sel.select(locKey(n), v);
            return;
        } catch (SeleniumException ex) {
            // XXX log?!?
        }
        sel.type(locKey(n), v);
    }
}

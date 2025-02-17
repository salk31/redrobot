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

/**
 * Simple interface for UI integration testing using visible text
 * and UI semantics.
 *
 * The locators "String... x" use fuzzy logic to find a single element.
 * Multiple values help to select between multiple matches.
 *
 * The exact rules of what elements are selected and how they are located
 * are implementation specific. In general they should aim to work
 * consistently across Robot and UI implementations where practical.
 */
public interface Robot {
  /**
   * Text to use to hit OK on a native JavaScript popup box.
   */
  String OK = "OK";

  /**
   * Text to use to hit CANCEL on a native JavaScript popup box.
   */
  String CANCEL = "CANCEL";

  /**
   * Move backwards in the client history.
   */
  void back();

  /**
   * Move forwards in the client history.
   */
  void forward();

  /**
   * Reload the current URL.
   */
  void reload();

  /**
   * Find and click an element.
   *
   * @param x - list of visible text to locate the element
   */
  void click(String... x);

  /**
   * Count the visible text matching x on this page.
   *
   * NB This method is inconsistent with other locator methods
   * and is being phased out.
   *
   * @param x - substring to be searched for
   * @return number of elements found
   */
  @Deprecated
  int findText(String x);

  /**
   * EXPERIMENTAL - More robust method to check if text on a page.
   *
   * NB Unlike other locators the final value has extra significance.
   * It is used to identify candidate elements that are then selected
   * between based on the other (if any) selector values.
   *
   * @param x - the list of locator strings to be used
   * @return true iff the locator text is present on the page and visible.
   */
  boolean textExists(String... x);

  /**
   * Find and return the value of a value UI control. e.g. Text Field
   *
   * @param x - list of visible text to locate the element
   * @return the control element
   */
  String get(String... x);

  /**
   * Does not hide the implementation of the popup
   * (native or HTML/JavaScript) so is being phased out.
   *
   * @return confirmation text
   */
  @Deprecated // use click(message, "OK")...
  String getConfirmation();

  /**
   * Does not hide the implementation of the control
   * so is being phased out.
   *
   * @param x locator text
   * @return true iff is checked
   */
  @Deprecated // use isSelected
  boolean isChecked(String... x);

  /**
   * Find and return the state of controls like checkboxes,
   * list box, radio button etc.
   *
   * @param x - list of visible text to locate the element
   * @return - true iff the item is selected
   */
  boolean isSelected(String... x);

  /**
   * Open the provided URL then wait till the browser is done.
   *
   * @param url - URL to open.
   */
  void open(URL url);

  /**
   * Set the content of an element that accepts user input.
   *
   * @param x - list of visible text to locate the element
   */
  void set(String... x);

  /**
   * Close the browser and any other used resources.
   */
  void close();


  /**
   * Allow the strategy for waiting for the browser to be ready
   * to be changed at any time.
   *
   * EXPERIMENTAL
   *
   * @param p the ReadyStrategy implementation.
   */
  void setReadyStrategy(ReadyStrategy p);
  // TODO 04 a dump/debug method?

  /**
   * Add a RobotListener which will be notified by various
   * events.
   *
   * Listeners are called in the order they were added.
   *
   * EXPERIMENTAL
   *
   * @param listener - listener to add.
   */
  void addListener(RobotListener listener);

  /**
   * Try and find an implementation instance of the class implClass.
   *
   * This will almost always be the browser interface implementation being used.
   *
   * @param implClass
   * @return the unwrapped instance
   */
  <T> T unwrap(Class<T> implClass);
}

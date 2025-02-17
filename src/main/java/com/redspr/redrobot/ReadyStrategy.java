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

/**
 * Pluggable code for waiting for a page to be ready.
 */
public interface ReadyStrategy {
  /**
   * Block while the browser is busy.
   *
   * Mixing JavaScript and modal dialogs won't work.
   * The single JavaScript thread is blocked so can't be used.
   *
   * Chrome supports findElement while modal is present.
   * Firefox clears the modal.
   */
  void waitTillReady();
}

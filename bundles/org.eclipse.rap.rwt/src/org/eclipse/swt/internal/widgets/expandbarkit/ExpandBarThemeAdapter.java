/*******************************************************************************
 * Copyright (c) 2009 EclipseSource and others. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   EclipseSource - initial API and implementation
 ******************************************************************************/
package org.eclipse.swt.internal.widgets.expandbarkit;

import org.eclipse.swt.internal.widgets.controlkit.ControlThemeAdapter;
import org.eclipse.swt.widgets.ExpandBar;


public class ExpandBarThemeAdapter  extends ControlThemeAdapter {

  public int getItemBorderWidth( final ExpandBar bar ) {
    return getCssBorderWidth( "ExpandItem", "border", bar );
  }

  public int getItemHeaderBorderWidth( final ExpandBar bar ) {
    return getCssBorderWidth( "ExpandItem-Header", "border", bar );
  }

}

/*******************************************************************************
 * Copyright (c) 2007, 2015 Innoopract Informationssysteme GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Innoopract Informationssysteme GmbH - initial API and implementation
 *    EclipseSource - ongoing development
 ******************************************************************************/
package org.eclipse.swt.internal.widgets.controlkit;

import org.eclipse.rap.rwt.internal.theme.ThemeAdapter;
import org.eclipse.rap.rwt.internal.theme.CssBoxDimensions;
import org.eclipse.rap.rwt.internal.theme.WidgetMatcher;
import org.eclipse.rap.rwt.theme.BoxDimensions;
import org.eclipse.rap.rwt.theme.ControlThemeAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;


public class ControlThemeAdapterImpl extends ThemeAdapter implements ControlThemeAdapter {

  @Override
  protected void configureMatcher( WidgetMatcher matcher ) {
    matcher.addStyle( "BORDER", SWT.BORDER );
  }

  public BoxDimensions getBorder( Control control ) {
    Rectangle value = getCssBorder( getPrimaryElement( control ), control );
    return new BoxDimensions( value.y, value.width - value.x, value.height - value.y, value.x );
  }

  public BoxDimensions getPadding( Control control ) {
    CssBoxDimensions value = getCssBoxDimensions( getPrimaryElement( control ), "padding", control );
    return new BoxDimensions( value.top, value.right, value.bottom, value.left );
  }

  public Color getForeground( Control control ) {
    return getCssColor( getPrimaryElement( control ), "color", control );
  }

  public Color getBackground( Control control ) {
    return getCssColor( getPrimaryElement( control ), "background-color", control );
  }

  public Font getFont( Control control ) {
    return getCssFont( getPrimaryElement( control ), "font", control );
  }

}

/*******************************************************************************
 * Copyright (c) 2015 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package org.eclipse.swt.internal.widgets;

import static org.eclipse.rap.rwt.internal.protocol.RemoteObjectFactory.getRemoteObject;
import static org.eclipse.rap.rwt.remote.JsonMapping.toJson;

import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;


public class ControlRemoteAdapter extends WidgetRemoteAdapter {

  private static final int PARENT = 11;
  private static final int BOUNDS = 12;
  private static final int CHILDREN = 13;
  private static final int TOOL_TIP_TEXT = 14;
  private static final int MENU = 15;
  private static final int FOREGROUND = 16;
  private static final int BACKGROUND = 17;
  private static final int FONT = 18;
  private static final int CURSOR = 19;
  private static final int VISIBLE = 20;
  private static final int ENABLED = 21;
  private static final int BACKGROUND_IMAGE = 22;
  private static final int ACTIVE_KEYS = 23;
  private static final int CANCEL_KEYS = 24;
  private static final int TAB_INDEX = 25;
  private static final int ORIENTATION = 26;

  private static final String PROP_BOUNDS = "bounds";
  private static final String PROP_CHILDREN = "children";
  private static final String PROP_TAB_INDEX = "tabIndex";
  private static final String PROP_MENU = "menu";
  private static final String PROP_VISIBLE = "visibility";
  private static final String PROP_ENABLED = "enabled";
  private static final String PROP_ORIENTATION = "direction";
  private static final String PROP_FOREGROUND = "foreground";
  private static final String PROP_BACKGROUND = "background";

  private transient Composite parent;
  private transient Control[] children;
  private transient Rectangle bounds;
  private transient int tabIndex;
  private transient String toolTipText;
  private transient Menu menu;
  private transient boolean visible;
  private transient boolean enabled;
  private transient boolean rtl;
  private transient Color foreground;
  private transient Color background;
  private transient boolean backgroundTransparency;
  private transient Image backgroundImage;
  private transient Font font;
  private transient Cursor cursor;
  private transient String[] activeKeys;
  private transient String[] cancelKeys;

  public ControlRemoteAdapter( String id ) {
    super( id );
  }

  public void preserveParent( Composite parent ) {
    markPreserved( PARENT );
    this.parent = parent;
  }

  public boolean hasPreservedParent() {
    return hasPreserved( PARENT );
  }

  public Composite getPreservedParent() {
    return parent;
  }

  public void preserveChildren( Control[] children ) {
    if( !hasPreserved( CHILDREN ) ) {
      markPreserved( CHILDREN );
      this.children = children;
    }
  }

  public void renderChildren( Composite composite ) {
    if( !isInitialized() || hasPreserved( CHILDREN ) ) {
      Control[] actual = composite.getChildren();
      if( changed( actual, children, null ) ) {
        getRemoteObject( getId() ).set( PROP_CHILDREN, toJson( actual ) );
      }
    }
  }

  public void preserveBounds( Rectangle bounds ) {
    if( !hasPreserved( BOUNDS ) ) {
      markPreserved( BOUNDS );
      this.bounds = bounds;
    }
  }

  public void renderBounds( IControlAdapter controlAdapter ) {
    if( !isInitialized() || hasPreserved( BOUNDS ) ) {
      Rectangle actual = controlAdapter.getBounds();
      if( changed( actual, bounds, null ) ) {
        getRemoteObject( getId() ).set( PROP_BOUNDS, toJson( actual ) );
      }
    }
  }

  public void preserveTabIndex( int tabIndex ) {
    if( !hasPreserved( TAB_INDEX ) ) {
      markPreserved( TAB_INDEX );
      this.tabIndex = tabIndex;
    }
  }

  public void renderTabIndex( Control control ) {
    if( hasPreserved( TAB_INDEX ) ) {
      int actual = ControlUtil.getControlAdapter( control ).getTabIndex();
      if( !isInitialized() || actual != tabIndex ) {
        getRemoteObject( control ).set( PROP_TAB_INDEX, actual );
      }
    }
  }

  public void preserveToolTipText( String toolTipText ) {
    markPreserved( TOOL_TIP_TEXT );
    this.toolTipText = toolTipText;
  }

  public boolean hasPreservedToolTipText() {
    return hasPreserved( TOOL_TIP_TEXT );
  }

  public String getPreservedToolTipText() {
    return toolTipText;
  }

  public void preserveMenu( Menu menu ) {
    if( !hasPreserved( MENU ) ) {
      markPreserved( MENU );
      this.menu = menu;
    }
  }

  public void renderMenu( Control control ) {
    if( hasPreserved( MENU )  ) {
      Menu actual = control.getMenu();
      if( changed( actual, menu, null ) ) {
        getRemoteObject( control ).set( PROP_MENU, toJson( actual ) );
      }
    }
  }

  public void preserveVisible( boolean visible ) {
    if( !hasPreserved( VISIBLE ) ) {
      markPreserved( VISIBLE );
      this.visible = visible;
    }
  }

  public void renderVisible( Control control ) {
    if( hasPreserved( VISIBLE ) ) {
      boolean actual = control.getVisible();
      if( changed( actual, visible, control instanceof Shell ? false : true ) ) {
        getRemoteObject( control ).set( PROP_VISIBLE, actual );
      }
    }
  }

  public void preserveEnabled( boolean enabled ) {
    if( !hasPreserved( ENABLED ) ) {
      markPreserved( ENABLED );
      this.enabled = enabled;
    }
  }

  public void renderEnabled( Control control ) {
    // Using isEnabled() would result in unnecessarily updating child widgets of
    // enabled/disabled controls.
    if( hasPreserved( ENABLED ) ) {
      boolean actual = control.getEnabled();
      if( changed( actual, enabled, true ) ) {
        getRemoteObject( control ).set( PROP_ENABLED, actual );
      }
    }
  }

  public void preserveOrientation( int orientation ) {
    if( !hasPreserved( ORIENTATION ) ) {
      markPreserved( ORIENTATION );
      rtl = orientation == SWT.RIGHT_TO_LEFT;
    }
  }

  public void renderOrientation( Control control ) {
    if( !isInitialized() || hasPreserved( ORIENTATION ) ) {
      // [if] Don't use control.getOrientation() as some controls (like SashForm) override this
      // method to return vertical/horizontal orientation only
      boolean actual = ( control.getStyle() & SWT.RIGHT_TO_LEFT ) == SWT.RIGHT_TO_LEFT;
      if( changed( actual, rtl, false ) ) {
        getRemoteObject( control ).set( PROP_ORIENTATION, actual ? "rtl" : "ltr" );
      }
    }
  }

  public void preserveForeground( Color foreground ) {
    if( !hasPreserved( FOREGROUND ) ) {
      markPreserved( FOREGROUND );
      this.foreground = foreground;
    }
  }

  public void renderForeground( IControlAdapter controlAdapter ) {
    if( hasPreserved( FOREGROUND ) ) {
      Color actual = controlAdapter.getUserForeground();
      if( changed( actual, foreground, null ) ) {
        getRemoteObject( getId() ).set( PROP_FOREGROUND, toJson( actual ) );
      }
    }
  }

  public void preserveBackground( Color background, boolean transparency ) {
    if( !hasPreserved( BACKGROUND ) ) {
      markPreserved( BACKGROUND );
      this.background = background;
      backgroundTransparency = transparency;
    }
  }

  public void renderBackground( IControlAdapter controlAdapter ) {
    if( hasPreserved( BACKGROUND ) ) {
      Color actualBackground = controlAdapter.getUserBackground();
      boolean actualTransparency = controlAdapter.getBackgroundTransparency();
      boolean colorChanged = changed( actualBackground, background, null );
      boolean transparencyChanged = changed( actualTransparency, backgroundTransparency, false );
      if( transparencyChanged || colorChanged ) {
        RGB rgb = null;
        int alpha = 0;
        if( actualBackground != null ) {
          rgb = actualBackground.getRGB();
          alpha = actualTransparency ? 0 : actualBackground.getAlpha();
        } else if( actualTransparency ) {
          rgb = new RGB( 0, 0, 0 );
        }
        getRemoteObject( getId() ).set( PROP_BACKGROUND, toJson( rgb, alpha ) );
      }
    }
  }

  public void preserveBackgroundImage( Image backgroundImage ) {
    markPreserved( BACKGROUND_IMAGE );
    this.backgroundImage = backgroundImage;
  }

  public boolean hasPreservedBackgroundImage() {
    return hasPreserved( BACKGROUND_IMAGE );
  }

  public Image getPreservedBackgroundImage() {
    return backgroundImage;
  }

  public void preserveFont( Font font ) {
    markPreserved( FONT );
    this.font = font;
  }

  public boolean hasPreservedFont() {
    return hasPreserved( FONT );
  }

  public Font getPreservedFont() {
    return font;
  }

  public void preserveCursor( Cursor cursor ) {
    markPreserved( CURSOR );
    this.cursor = cursor;
  }

  public boolean hasPreservedCursor() {
    return hasPreserved( CURSOR );
  }

  public Cursor getPreservedCursor() {
    return cursor;
  }

  public void preserveActiveKeys( String[] activeKeys ) {
    markPreserved( ACTIVE_KEYS );
    this.activeKeys = activeKeys;
  }

  public boolean hasPreservedActiveKeys() {
    return hasPreserved( ACTIVE_KEYS );
  }

  public String[] getPreservedActiveKeys() {
    return activeKeys;
  }

  public void preserveCancelKeys( String[] cancelKeys ) {
    markPreserved( CANCEL_KEYS );
    this.cancelKeys = cancelKeys;
  }

  public boolean hasPreservedCancelKeys() {
    return hasPreserved( CANCEL_KEYS );
  }

  public String[] getPreservedCancelKeys() {
    return cancelKeys;
  }

  @Override
  public void clearPreserved() {
    super.clearPreserved();
    parent = null;
    children = null;
    bounds = null;
    tabIndex = 0;
    toolTipText = null;
    menu = null;
    visible = false;
    enabled = false;
    rtl = false;
    foreground = null;
    background = null;
    backgroundTransparency = false;
    backgroundImage = null;
    font = null;
    cursor = null;
    activeKeys = null;
    cancelKeys = null;
  }

  private Object readResolve() {
    initialize();
    return this;
  }

  private boolean changed( boolean actualValue, boolean preservedValue, boolean defaultValue ) {
    return actualValue != ( isInitialized() ? preservedValue : defaultValue );
  }

  private boolean changed( Object actualValue, Object preservedValue, Object defaultValue ) {
    return !equals( actualValue, isInitialized() ? preservedValue : defaultValue );
  }

  private boolean changed( Object[] actualValue, Object[] preservedValue, Object[] defaultValue ) {
    return !Arrays.equals( actualValue, isInitialized() ? preservedValue : defaultValue );
  }

  private static boolean equals( Object o1, Object o2 ) {
    return o1 == o2 || o1 != null && o1.equals( o2 );
  }

}

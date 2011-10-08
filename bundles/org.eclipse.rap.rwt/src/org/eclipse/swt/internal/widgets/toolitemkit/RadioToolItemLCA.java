/*******************************************************************************
 * Copyright (c) 2002, 2011 Innoopract Informationssysteme GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Innoopract Informationssysteme GmbH - initial API and implementation
 *    EclipseSource - ongoing development
 ******************************************************************************/
package org.eclipse.swt.internal.widgets.toolitemkit;

import java.io.IOException;

import org.eclipse.rwt.internal.lifecycle.JSConst;
import org.eclipse.rwt.lifecycle.WidgetLCAUtil;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.internal.events.DeselectionEvent;
import org.eclipse.swt.internal.events.EventLCAUtil;
import org.eclipse.swt.widgets.ToolItem;


final class RadioToolItemLCA extends ToolItemDelegateLCA {

  private static final String PARAM_SELECTION = "selection";

  void preserveValues( final ToolItem toolItem ) {
    ToolItemLCAUtil.preserveValues( toolItem );
  }

  void readData( ToolItem toolItem ) {
    String value = WidgetLCAUtil.readPropertyValue( toolItem, PARAM_SELECTION );
    if( value != null ) {
      toolItem.setSelection( Boolean.valueOf( value ).booleanValue() );
      processSelectionEvent( toolItem );
    }
  }

  void renderInitialization( ToolItem toolItem ) throws IOException {
    ToolItemLCAUtil.renderInitialization( toolItem );
  }

  void renderChanges( ToolItem toolItem ) throws IOException {
    ToolItemLCAUtil.renderChanges( toolItem );
  }

  private static void processSelectionEvent( ToolItem toolItem ) {
    if( SelectionEvent.hasListener( toolItem ) ) {
      int type = SelectionEvent.WIDGET_SELECTED;
      SelectionEvent event;
      if( toolItem.getSelection() ) {
        event = new SelectionEvent( toolItem, null, type );
      } else {
        event = new DeselectionEvent( toolItem, null, type );
      }
      event.stateMask = EventLCAUtil.readStateMask( JSConst.EVENT_WIDGET_SELECTED_MODIFIER );
      event.processEvent();
    }

  }
}

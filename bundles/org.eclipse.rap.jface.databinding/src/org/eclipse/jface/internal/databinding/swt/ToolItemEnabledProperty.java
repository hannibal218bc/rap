/*******************************************************************************
 * Copyright (c) 2009 Matthew Hall and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthew Hall - initial API and implementation (bug 280157)
 ******************************************************************************/

package org.eclipse.jface.internal.databinding.swt;

import org.eclipse.swt.widgets.ToolItem;

/**
 * @since 1.4
 */
public class ToolItemEnabledProperty extends WidgetBooleanValueProperty<ToolItem> {
	@Override
	protected boolean doGetBooleanValue(ToolItem source) {
		return source.getEnabled();
	}

	@Override
	protected void doSetBooleanValue(ToolItem source, boolean value) {
		source.setEnabled(value);
	}

	@Override
	public String toString() {
		return "ToolItem.enabled <boolean>"; //$NON-NLS-1$
	}
}

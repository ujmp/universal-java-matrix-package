/*
 * Copyright (C) 2008-2014 by Holger Arndt
 *
 * This file is part of the Universal Java Matrix Package (UJMP).
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * UJMP is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * UJMP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with UJMP; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package org.ujmp.gui.util;

import java.awt.Color;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

public class UIDefaults {

	public static final RenderingHints AALIAS = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

	public static final Color SELECTEDCOLOR = new Color(100, 100, 255);

	public static void setDefaults() {

	}

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
		}

		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);

		try {
			Locale.setDefault(Locale.US);
		} catch (Throwable e) {
		}

		UIManager.put("Table.rowHeight", 32);
		UIManager.put("Table.iconWidth", 32);

		UIManager.put("Table.paddingX", 2);
		UIManager.put("Table.paddingY", 2);

		UIManager.put("JDMP.defaultInsets", new Insets(5, 5, 5, 5));

		ClassLoader cl = UIDefaults.class.getClassLoader();

		// TODO no icons available
		if (false) {

			UIManager.put("JDMP.icon.Image", new ImageIcon(cl.getResource("icons/image.png")));

			UIManager.put("JDMP.icon.Variable", new ImageIcon("resources/icons/variable.png"));
			UIManager.put("JDMP.icon.Module", new ImageIcon("resources/icons/module.png"));
			UIManager.put("JDMP.icon.DataSet", new ImageIcon("resources/icons/dataset.png"));
			UIManager.put("JDMP.icon.Algorithm", new ImageIcon("resources/icons/algorithm.png"));
			UIManager.put("JDMP.icon.Sample", new ImageIcon("resources/icons/sample.png"));

			UIManager.put("JDMP.icon.StartAction", new ImageIcon("resources/icons/start.png"));
			UIManager.put("JDMP.icon.StopAction", new ImageIcon("resources/icons/stop.png"));
			UIManager.put("JDMP.icon.AboutAction", new ImageIcon(cl.getResource("icons/about.png")));
			UIManager.put("JDMP.icon.CalculateAction", new ImageIcon(cl.getResource("icons/calculate.png")));
			UIManager.put("JDMP.icon.ClearAction", new ImageIcon(cl.getResource("icons/new.png")));
			UIManager.put("JDMP.icon.CloseAction", new ImageIcon(cl.getResource("icons/close.png")));
			UIManager.put("JDMP.icon.CopyAction", new ImageIcon(cl.getResource("icons/copy.png")));
			UIManager.put("JDMP.icon.CutAction", new ImageIcon(cl.getResource("icons/cut.png")));
			UIManager.put("JDMP.icon.DeleteAction", new ImageIcon(cl.getResource("icons/delete.png")));
			UIManager.put("JDMP.icon.ExitAction", new ImageIcon(cl.getResource("icons/exit.png")));
			UIManager.put("JDMP.icon.ExportAction", new ImageIcon(cl.getResource("icons/export.png")));
			UIManager.put("JDMP.icon.NewAction", new ImageIcon(cl.getResource("icons/new.png")));
			UIManager.put("JDMP.icon.NextAction", new ImageIcon(cl.getResource("icons/next.png")));
			UIManager.put("JDMP.icon.PrintAction", new ImageIcon(cl.getResource("icons/print.png")));
			UIManager.put("JDMP.icon.SaveAction", new ImageIcon(cl.getResource("icons/save.png")));
			UIManager.put("JDMP.icon.SaveAsAction", new ImageIcon(cl.getResource("icons/saveas.png")));
			UIManager.put("JDMP.icon.ShuffleAction", new ImageIcon(cl.getResource("icons/shuffle.png")));
			UIManager.put("JDMP.icon.StartAction", new ImageIcon(cl.getResource("icons/start.png")));
			UIManager.put("JDMP.icon.StopAction", new ImageIcon(cl.getResource("icons/stop.png")));
			UIManager.put("JDMP.icon.PasteAction", new ImageIcon(cl.getResource("icons/paste.png")));
		}

	}
}

// AbstractButton.clickText
// AbstractDocument.additionText
// AbstractDocument.deletionText
// AbstractDocument.redoText
// AbstractDocument.styleChangeText
// AbstractDocument.undoText
// AbstractUndoableEdit.redoText
// AbstractUndoableEdit.undoText
// Button.background
// Button.border
// Button.disabledText
// Button.focus
// Button.focusInputMap
// Button.font
// Button.foreground
// Button.margin
// Button.select
// Button.textIconGap
// Button.textShiftOffset
// ButtonUI
// CheckBox.background
// CheckBox.border
// CheckBox.disabledText
// CheckBox.focus
// CheckBox.focusInputMap
// CheckBox.font
// CheckBox.foreground
// CheckBox.icon
// CheckBox.margin
// CheckBox.textIconGap
// CheckBox.textShiftOffset
// CheckBoxMenuItem.acceleratorFont
// CheckBoxMenuItem.acceleratorForeground
// CheckBoxMenuItem.acceleratorSelectionForeground
// CheckBoxMenuItem.arrowIcon
// CheckBoxMenuItem.background
// CheckBoxMenuItem.border
// CheckBoxMenuItem.borderPainted
// CheckBoxMenuItem.checkIcon
// CheckBoxMenuItem.disabledForeground
// CheckBoxMenuItem.font
// CheckBoxMenuItem.foreground
// CheckBoxMenuItem.margin
// CheckBoxMenuItem.selectionBackground
// CheckBoxMenuItem.selectionForeground
// CheckBoxMenuItemUI
// CheckBoxUI
// Checkbox.select
// ColorChooser.background
// ColorChooser.cancelText
// ColorChooser.font
// ColorChooser.foreground
// ColorChooser.hsbBlueText
// ColorChooser.hsbBrightnessText
// ColorChooser.hsbGreenText
// ColorChooser.hsbHueText
// ColorChooser.hsbNameText
// ColorChooser.hsbRedText
// ColorChooser.hsbSaturationText
// ColorChooser.okText
// ColorChooser.previewText
// ColorChooser.resetText
// ColorChooser.rgbBlueMnemonic
// ColorChooser.rgbBlueText
// ColorChooser.rgbGreenMnemonic
// ColorChooser.rgbGreenText
// ColorChooser.rgbNameText
// ColorChooser.rgbRedMnemonic
// ColorChooser.rgbRedText
// ColorChooser.sampleText
// ColorChooser.swatchesDefaultRecentColor
// ColorChooser.swatchesNameText
// ColorChooser.swatchesRecentSwatchSize
// ColorChooser.swatchesRecentText
// ColorChooser.swatchesSwatchSize
// ColorChooserUI
// ComboBox.ancestorInputMap
// ComboBox.background
// ComboBox.disabledBackground
// ComboBox.disabledForeground
// ComboBox.font
// ComboBox.foreground
// ComboBox.listBackground
// ComboBox.listForeground
// ComboBox.selectionBackground
// ComboBox.selectionForeground
// ComboBox.togglePopupText
// ComboBoxUI
// Desktop.ancestorInputMap
// Desktop.background
// DesktopIcon.background
// DesktopIcon.border
// DesktopIcon.font
// DesktopIcon.foreground
// DesktopIconUI
// DesktopPaneUI
// EditorPane.background
// EditorPane.border
// EditorPane.caretBlinkRate
// EditorPane.caretForeground
// EditorPane.focusInputMap
// EditorPane.font
// EditorPane.foreground
// EditorPane.inactiveForeground
// EditorPane.keyBindings
// EditorPane.margin
// EditorPane.selectionBackground
// EditorPane.selectionForeground
// EditorPaneUI
// FileChooser.acceptAllFileFilterText
// FileChooser.ancestorInputMap
// FileChooser.cancelButtonMnemonic
// FileChooser.cancelButtonText
// FileChooser.cancelButtonToolTipText
// FileChooser.detailsViewButtonAccessibleName
// FileChooser.detailsViewButtonToolTipText
// FileChooser.detailsViewIcon
// FileChooser.directoryDescriptionText
// FileChooser.fileDescriptionText
// FileChooser.fileNameLabelMnemonic
// FileChooser.fileNameLabelText
// FileChooser.filesOfTypeLabelMnemonic
// FileChooser.filesOfTypeLabelText
// FileChooser.helpButtonMnemonic
// FileChooser.helpButtonText
// FileChooser.helpButtonToolTipText
// FileChooser.homeFolderAccessibleName
// FileChooser.homeFolderIcon
// FileChooser.homeFolderToolTipText
// FileChooser.listViewButtonAccessibleName
// FileChooser.listViewButtonToolTipText
// FileChooser.listViewIcon
// FileChooser.lookInLabelMnemonic
// FileChooser.lookInLabelText
// FileChooser.newFolderAccessibleName
// FileChooser.newFolderErrorSeparator
// FileChooser.newFolderErrorText
// FileChooser.newFolderIcon
// FileChooser.newFolderToolTipText
// FileChooser.openButtonMnemonic
// FileChooser.openButtonText
// FileChooser.openButtonToolTipText
// FileChooser.openDialogTitleText
// FileChooser.other.newFolder
// FileChooser.other.newFolder.subsequent
// FileChooser.saveButtonMnemonic
// FileChooser.saveButtonText
// FileChooser.saveButtonToolTipText
// FileChooser.saveDialogTitleText
// FileChooser.upFolderAccessibleName
// FileChooser.upFolderIcon
// FileChooser.upFolderToolTipText
// FileChooser.updateButtonMnemonic
// FileChooser.updateButtonText
// FileChooser.updateButtonToolTipText
// FileChooser.win32.newFolder
// FileChooser.win32.newFolder.subsequent
// FileChooserUI
// FileView.computerIcon
// FileView.directoryIcon
// FileView.fileIcon
// FileView.floppyDriveIcon
// FileView.hardDriveIcon
// FocusManagerClassName
// FormView.resetButtonText
// FormView.submitButtonText
// InternalFrame.activeTitleBackground
// InternalFrame.activeTitleForeground
// InternalFrame.border
// InternalFrame.closeIcon
// InternalFrame.font
// InternalFrame.icon
// InternalFrame.iconifyIcon
// InternalFrame.inactiveTitleBackground
// InternalFrame.inactiveTitleForeground
// InternalFrame.maximizeIcon
// InternalFrame.minimizeIcon
// InternalFrame.optionDialogBorder
// InternalFrame.paletteBorder
// InternalFrame.paletteCloseIcon
// InternalFrame.paletteTitleHeight
// InternalFrame.titleFont
// InternalFrame.windowBindings
// InternalFrameTitlePane.closeButtonAccessibleName
// InternalFrameTitlePane.closeButtonText
// InternalFrameTitlePane.iconifyButtonAccessibleName
// InternalFrameTitlePane.maximizeButtonAccessibleName
// InternalFrameTitlePane.maximizeButtonText
// InternalFrameTitlePane.minimizeButtonText
// InternalFrameTitlePane.moveButtonText
// InternalFrameTitlePane.restoreButtonText
// InternalFrameTitlePane.sizeButtonText
// InternalFrameUI
// Label.background
// Label.disabledForeground
// Label.disabledShadow
// Label.font
// Label.foreground
// LabelUI
// List.background
// List.cellRenderer
// List.focusCellHighlightBorder
// List.focusInputMap
// List.font
// List.foreground
// List.selectionBackground
// List.selectionForeground
// ListUI
// Menu.acceleratorFont
// Menu.acceleratorForeground
// Menu.acceleratorSelectionForeground
// Menu.arrowIcon
// Menu.background
// Menu.border
// Menu.borderPainted
// Menu.checkIcon
// Menu.consumesTabs
// Menu.disabledForeground
// Menu.font
// Menu.foreground
// Menu.margin
// Menu.selectedWindowInputMapBindings
// Menu.selectionBackground
// Menu.selectionForeground
// MenuBar.background
// MenuBar.border
// MenuBar.font
// MenuBar.foreground
// MenuBar.windowBindings
// MenuBarUI
// MenuItem.acceleratorDelimiter
// MenuItem.acceleratorFont
// MenuItem.acceleratorForeground
// MenuItem.acceleratorSelectionForeground
// MenuItem.arrowIcon
// MenuItem.background
// MenuItem.border
// MenuItem.borderPainted
// MenuItem.checkIcon
// MenuItem.disabledForeground
// MenuItem.font
// MenuItem.foreground
// MenuItem.margin
// MenuItem.selectionBackground
// MenuItem.selectionForeground
// MenuItemUI
// MenuUI
// OptionPane.background
// OptionPane.border
// OptionPane.buttonAreaBorder
// OptionPane.cancelButtonText
// OptionPane.errorIcon
// OptionPane.font
// OptionPane.foreground
// OptionPane.informationIcon
// OptionPane.messageAreaBorder
// OptionPane.messageForeground
// OptionPane.minimumSize
// OptionPane.noButtonText
// OptionPane.okButtonText
// OptionPane.questionIcon
// OptionPane.titleText
// OptionPane.warningIcon
// OptionPane.windowBindings
// OptionPane.yesButtonText
// OptionPaneUI
// Panel.background
// Panel.font
// Panel.foreground
// PanelUI
// PasswordField.background
// PasswordField.border
// PasswordField.caretBlinkRate
// PasswordField.caretForeground
// PasswordField.focusInputMap
// PasswordField.font
// PasswordField.foreground
// PasswordField.inactiveForeground
// PasswordField.keyBindings
// PasswordField.margin
// PasswordField.selectionBackground
// PasswordField.selectionForeground
// PasswordFieldUI
// PopupMenu.background
// PopupMenu.border
// PopupMenu.font
// PopupMenu.foreground
// PopupMenu.selectedWindowInputMapBindings
// PopupMenuSeparatorUI
// PopupMenuUI
// ProgressBar.background
// ProgressBar.backgroundHighlight
// ProgressBar.border
// ProgressBar.cellLength
// ProgressBar.cellSpacing
// ProgressBar.font
// ProgressBar.foreground
// ProgressBar.foregroundHighlight
// ProgressBar.selectionBackground
// ProgressBar.selectionForeground
// ProgressBarUI
// ProgressMonitor.progressText
// RadioButton.background
// RadioButton.border
// RadioButton.disabledText
// RadioButton.focus
// RadioButton.focusInputMap
// RadioButton.font
// RadioButton.foreground
// RadioButton.icon
// RadioButton.margin
// RadioButton.select
// RadioButton.textIconGap
// RadioButton.textShiftOffset
// RadioButtonMenuItem.acceleratorFont
// RadioButtonMenuItem.acceleratorForeground
// RadioButtonMenuItem.acceleratorSelectionForeground
// RadioButtonMenuItem.arrowIcon
// RadioButtonMenuItem.background
// RadioButtonMenuItem.border
// RadioButtonMenuItem.borderPainted
// RadioButtonMenuItem.checkIcon
// RadioButtonMenuItem.disabledForeground
// RadioButtonMenuItem.font
// RadioButtonMenuItem.foreground
// RadioButtonMenuItem.margin
// RadioButtonMenuItem.selectionBackground
// RadioButtonMenuItem.selectionForeground
// RadioButtonMenuItemUI
// RadioButtonUI
// RootPane.defaultButtonWindowKeyBindings
// RootPaneUI
// ScrollBar.allowsAbsolutePositioning
// ScrollBar.background
// ScrollBar.darkShadow
// ScrollBar.focusInputMap
// ScrollBar.foreground
// ScrollBar.highlight
// ScrollBar.maximumThumbSize
// ScrollBar.minimumThumbSize
// ScrollBar.shadow
// ScrollBar.thumb
// ScrollBar.thumbDarkShadow
// ScrollBar.thumbHighlight
// ScrollBar.thumbLightShadow
// ScrollBar.thumbShadow
// ScrollBar.track
// ScrollBar.trackHighlight
// ScrollBar.width
// ScrollBarUI
// ScrollPane.ancestorInputMap
// ScrollPane.background
// ScrollPane.border
// ScrollPane.font
// ScrollPane.foreground
// ScrollPaneUI
// Separator.background
// Separator.foreground
// Separator.highlight
// Separator.shadow
// SeparatorUI
// Slider.background
// Slider.focus
// Slider.focusInputMap
// Slider.focusInsets
// Slider.foreground
// Slider.highlight
// Slider.horizontalThumbIcon
// Slider.majorTickLength
// Slider.shadow
// Slider.trackWidth
// Slider.verticalThumbIcon
// SliderUI
// SplitPane.ancestorInputMap
// SplitPane.background
// SplitPane.border
// SplitPane.dividerSize
// SplitPane.highlight
// SplitPane.leftButtonText
// SplitPane.rightButtonText
// SplitPane.shadow
// SplitPaneDivider.border
// SplitPaneUI
// StandardDialogUI
// TabbedPane.ancestorInputMap
// TabbedPane.background
// TabbedPane.contentBorderInsets
// TabbedPane.darkShadow
// TabbedPane.focus
// TabbedPane.focusInputMap
// TabbedPane.font
// TabbedPane.foreground
// TabbedPane.highlight
// TabbedPane.lightHighlight
// TabbedPane.selectHighlight
// TabbedPane.selected
// TabbedPane.selectedTabPadInsets
// TabbedPane.shadow
// TabbedPane.tabAreaBackground
// TabbedPane.tabAreaInsets
// TabbedPane.tabInsets
// TabbedPane.tabRunOverlay
// TabbedPane.textIconGap
// TabbedPaneUI
// Table.ancestorInputMap
// Table.background
// Table.focusCellBackground
// Table.focusCellForeground
// Table.focusCellHighlightBorder
// Table.font
// Table.foreground
// Table.gridColor
// Table.scrollPaneBorder
// Table.selectionBackground
// Table.selectionForeground
// TableHeader.background
// TableHeader.cellBorder
// TableHeader.font
// TableHeader.foreground
// TableHeaderUI
// TableUI
// TextArea.background
// TextArea.border
// TextArea.caretBlinkRate
// TextArea.caretForeground
// TextArea.focusInputMap
// TextArea.font
// TextArea.foreground
// TextArea.inactiveForeground
// TextArea.keyBindings
// TextArea.margin
// TextArea.selectionBackground
// TextArea.selectionForeground
// TextAreaUI
// TextField.background
// TextField.border
// TextField.caretBlinkRate
// TextField.caretForeground
// TextField.focusInputMap
// TextField.font
// TextField.foreground
// TextField.inactiveForeground
// TextField.keyBindings
// TextField.margin
// TextField.selectionBackground
// TextField.selectionForeground
// TextFieldUI
// TextPane.background
// TextPane.border
// TextPane.caretBlinkRate
// TextPane.caretForeground
// TextPane.focusInputMap
// TextPane.font
// TextPane.foreground
// TextPane.inactiveForeground
// TextPane.keyBindings
// TextPane.margin
// TextPane.selectionBackground
// TextPane.selectionForeground
// TextPaneUI
// TitledBorder.border
// TitledBorder.font
// TitledBorder.titleColor
// ToggleButton.background
// ToggleButton.border
// ToggleButton.disabledBackground
// ToggleButton.disabledSelectedBackground
// ToggleButton.disabledSelectedText
// ToggleButton.disabledText
// ToggleButton.focus
// ToggleButton.focusInputMap
// ToggleButton.font
// ToggleButton.foreground
// ToggleButton.margin
// ToggleButton.select
// ToggleButton.text
// ToggleButton.textIconGap
// ToggleButton.textShiftOffset
// ToggleButtonUI
// ToolBar.ancestorInputMap
// ToolBar.background
// ToolBar.border
// ToolBar.dockingBackground
// ToolBar.dockingForeground
// ToolBar.floatingBackground
// ToolBar.floatingForeground
// ToolBar.font
// ToolBar.foreground
// ToolBar.separatorSize
// ToolBarSeparatorUI
// ToolBarUI
// ToolTip.background
// ToolTip.border
// ToolTip.font
// ToolTip.foreground
// ToolTipUI
// Tree.ancestorInputMap
// Tree.background
// Tree.changeSelectionWithFocus
// Tree.closedIcon
// Tree.collapsedIcon
// Tree.drawsFocusBorderAroundIcon
// Tree.editorBorder
// Tree.expandedIcon
// Tree.focusInputMap
// Tree.font
// Tree.foreground
// Tree.hash
// Tree.leafIcon
// Tree.leftChildIndent
// Tree.line
// Tree.openIcon
// Tree.rightChildIndent
// Tree.rowHeight
// Tree.scrollsOnExpand
// Tree.selectionBackground
// Tree.selectionBorderColor
// Tree.selectionForeground
// Tree.textBackground
// Tree.textForeground
// TreeUI
// Viewport.background
// Viewport.font
// Viewport.foreground
// ViewportUI
// activeCaption
// activeCaptionBorder
// activeCaptionText
// control
// controlDkShadow
// controlHighlight
// controlLtHighlight
// controlShadow
// controlText
// desktop
// inactiveCaption
// inactiveCaptionBorder
// inactiveCaptionText
// info
// infoText
// menu
// menuText
// scrollbar
// text
// textHighlight
// textHighlightText
// textInactiveText
// textText
// window
// windowBorder
// windowText

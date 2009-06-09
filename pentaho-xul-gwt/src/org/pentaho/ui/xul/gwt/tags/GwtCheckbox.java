package org.pentaho.ui.xul.gwt.tags;

import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulCheckbox;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.gwt.AbstractGwtXulComponent;
import org.pentaho.ui.xul.gwt.GwtXulHandler;
import org.pentaho.ui.xul.gwt.GwtXulParser;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Widget;

public class GwtCheckbox extends AbstractGwtXulComponent implements XulCheckbox {
  
  static final String ELEMENT_NAME = "checkbox"; //$NON-NLS-1$
  private String command;
  private boolean checked;
  public static void register() {
    GwtXulParser.registerHandler(ELEMENT_NAME, 
    new GwtXulHandler() {
      public Element newInstance() {
        return new GwtCheckbox();
      }
    });
  }
  
  private CheckBox checkBox;
  
  public GwtCheckbox() {
    super(ELEMENT_NAME);
    managedObject = checkBox = new CheckBox();
    checkBox.setStylePrimaryName("checkbox");
  }
  
  public void init(com.google.gwt.xml.client.Element srcEle, XulDomContainer container) {
    super.init(srcEle, container);
    setLabel(srcEle.getAttribute("label"));
    setChecked("true".equals(srcEle.getAttribute("checked")));
    setDisabled("true".equals(srcEle.getAttribute("disabled")));
    String command = srcEle.getAttribute("command");
    if (command != null && command.trim().length() > 0) {
      setCommand(command);
    }
    if(srcEle.getAttribute("pen:class") != null && srcEle.getAttribute("pen:class").length() > 0){
      setClass(srcEle.getAttribute("pen:class"));
    }
  }
  
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulCheckbox#getSelected()
   */
  public boolean getSelected() {
    // TODO Auto-generated method stub
    return checkBox.isChecked();
  }
  /* (non-Javadoc)
   * @see org.pentaho.ui.xul.components.XulCheckbox#setSelected(boolean)
   */
  public void setSelected(boolean selected) {
    checkBox.setChecked(selected);
  }
  

  public void layout(){
    checkBox.setTitle(this.getTooltiptext());
  }
  
  public void setLabel(String label){
    checkBox.setText(label);
  }

  public String getLabel() {
    return checkBox.getText();
  }

  public boolean isChecked() {
    return checkBox.isChecked();

  }

  public boolean isDisabled() {
    return !checkBox.isEnabled();
  }

  public void setChecked(boolean checked) {
   boolean previousVal = this.checked;
   checkBox.setChecked(checked); 
   this.checked = checked;
   this.firePropertyChange("checked", previousVal, checked);
  }

  public void setDisabled(boolean dis) {
    checkBox.setEnabled(!dis);
  }

  public void setCommand(final String method) {
    checkBox.addClickListener(new ClickListener(){
      public void onClick(Widget sender) {
        try{
          GwtCheckbox.this.getXulDomContainer().invoke(method, new Object[]{});
        } catch(XulException e){
          e.printStackTrace();
        }
      }
    });
  }
  
  public String getCommand(){
    return command;
  }
  
  public void adoptAttributes(XulComponent component) {

    if(component.getAttributeValue("label") != null){
      setLabel(component.getAttributeValue("label"));
    }
    if(component.getAttributeValue("checked") != null){
      setChecked("true".equals(component.getAttributeValue("checked")));
    }
    if(component.getAttributeValue("disabled") != null){
      setDisabled("true".equals(component.getAttributeValue("disabled")));
    }
  }
  
  public void setClass(String className){
    checkBox.setStylePrimaryName(className);
  }
}

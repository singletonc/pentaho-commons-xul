package org.pentaho.ui.xul.swt.tags;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.pentaho.ui.xul.XulComponent;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.components.XulButton;
import org.pentaho.ui.xul.components.XulButton.Type;
import org.pentaho.ui.xul.containers.XulWindow;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.dom.Element;
import org.pentaho.ui.xul.swt.SwtElement;
import org.pentaho.ui.xul.util.Direction;

public class SwtButton extends SwtElement implements XulButton {
  private static final long serialVersionUID = -7218075117194366698L;
  private static final Log logger = LogFactory.getLog(SwtButton.class);

  protected org.eclipse.swt.widgets.Button button;
  private String label;
  private boolean disabled;
  private String image;
  private Direction dir;
  private Type type;
  private String group;
  private String onclick;
  

  public SwtButton(XulComponent parent, XulDomContainer container, String tagName) {
    super(tagName);
    button = createNewButton((Composite)parent.getManagedObject());
    managedObject = button;
  }
  
  protected Button createNewButton(Composite parent) {
    return new Button(parent, SWT.NONE);
  }

  
  /**
   * XUL's attribute is "disabled", thus this acts
   * exactly the opposite of SWT. If the property is not 
   * available, then the control is enabled. 
   * @return boolean true if the control is disabled.
   */
  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
    if (!button.isDisposed()) button.setEnabled( !disabled );
  }

  public void setOnclick(final String method) {
    this.onclick = method;
    button.addSelectionListener(new SelectionAdapter(){
      public void widgetSelected(org.eclipse.swt.events.SelectionEvent arg0){
        Document doc = getDocument();
        XulWindow window = (XulWindow) doc.getRootElement();
        XulDomContainer container = window.getXulDomContainer();
        
        try{
          container.invoke(method, new Object[]{});
        } catch (XulException e){
          logger.error("Error calling oncommand event",e);
        }
      }
    });

  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
    button.setText(label);
  }

  public String getImage() {
    return this.image;
      
  }

  public void setImage(String src) {
   this.image = src;   
  }

  public String getDir() {
    return dir.toString().toLowerCase();
  }

  public void setDir(String dir) {
    this.dir = Direction.valueOf(dir.toUpperCase());
  }

  public String getGroup() {
    return this.group;
  }

  public String getType() {
    return this.type.toString();
  }

  public void setGroup(String group) {
    this.group = group;
  }

  public void setType(String type) {
    this.type = Type.valueOf(type.toString().toUpperCase());
    
  }

  public String getOnclick() {
    return this.onclick;
  }
}

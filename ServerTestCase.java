import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class ServerTestCase implements RequestListener
{
//-----------------------------------------------
private TMNetworkServer tmNetworkServer;
//-----------------------------------------------
public Application()
{
tmNetworkServer=new TMNetworkServer(5000,5001,this);
System.out.println("Server instantiated");
}
//-----------------------------------------------
public void onError(Client client)
{
}
public void onClose(Client client)
{
}
public void waitForApplicationToEnd()
{
this.tmNetworkServer.waitForServerToStop();
}

public static void main(String gg[])
{
Application application=new Application();
application.waitForApplicationToEnd();
}

public Object onData (Client client,String actionType,Object object)
{
System.out.println("Request arrived for action : "+actionType);
System.out.println("Data arrived : "+object);
String data=(String)object;

if(data.equals("hell"))
{
return new RuntimeException("Invalid request");
}
if(data.equals("bad"))
{
throw new RuntimeException("Very bad");
}
return data+"server Se aya";
}

public void onOpen(Client client)
{
Container container;
JLabel label;
JTextField textField;
JButton button;
JFrame frame = new JFrame();
label=new JLabel("                                            ");
textField=new JTextField(20);
button=new JButton("Send");
container=frame.getContentPane();
container.setLayout(new FlowLayout());
container.add(textField);
container.add(button);
container.add(label);
button.addActionListener(new ActionListener(){

public void actionPerformed(ActionEvent ev)
{
String g=textField.getText();
System.out.println("Sending request");
//------------------------------------------------------
tmNetworkServer.sendRequest(client,"remove",g,new ResponseListener(){
public void onError(String error)
{
label.setText("Error : "+error);
System.out.println("Error : "+error);
}
public void onException(Throwable throwable)
{
label.setText("Exception : "+throwable.getMessage());
System.out.println("Exception : "+throwable.getMessage());
}
public void onResponse(Object object)
{
String rr=(String)object;
System.out.println("Result "+rr);
label.setText("Result : "+rr);
}
});
}
});

frame.setLocation(510,10);
frame.setSize(500,400);
frame.setTitle("Server For "+ client.getClientId());
frame.setVisible(true);
}
}
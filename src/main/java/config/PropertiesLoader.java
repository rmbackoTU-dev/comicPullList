package config;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesLoader {
    
    private static String propFile;
    public Properties props;

    /**
     * Constructor Load a static property file location into our loader
     * from the classpath
     * @param propertiesPathLocation Should be the path of a resource relative to the classpath or /java folder
     */
    public PropertiesLoader(String propertiesPathLocation)
    {
       propFile=propertiesPathLocation;
       props=new Properties();
    }

    /**
     * Function: loadProps()
     * Description: Loads the properties in to the property file
     * This is called internally to load props if props.isEmpty
     */
    private void loadProps()
    {
        try
        {
            InputStream in=PropertiesLoader.class.getClassLoader().getResourceAsStream(propFile);
            props.load(in);
        }
        catch(IOException ioe)
        {
            System.err.println(ioe.getMessage());
        }
        //if it is not empty use what we have unless an update is called
    }

    public Properties getProps()
    {
        Properties scopedProps;
        if(props.isEmpty())
        {
            loadProps();
            scopedProps=props;
        }
        else
        {
            scopedProps=props;
        }
        return scopedProps;

    }
}

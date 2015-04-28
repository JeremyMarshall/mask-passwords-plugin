package com.michelin.cio.hudson.plugins.maskpasswords.dsl;

import hudson.Extension;
import org.jenkinsci.plugins.jobdsl.stub.DslClosureUnsupported;
import org.jenkinsci.plugins.jobdsl.stub.DslNoClosureClass;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.BuildWrapper;
import com.michelin.cio.hudson.plugins.maskpasswords.MaskPasswordsBuildWrapper.VarPasswordPair;
import com.michelin.cio.hudson.plugins.maskpasswords.MaskPasswordsBuildWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jeremymarshall on 27/04/15.
 */
@Extension
public class MaskPasswordsBW extends BuildWrapper{
    @Override
    public String getName(){
        return "maskPassword";
    }

    @Override
    public String getDescription(){
        return "Mask a Password for this project";
    }

    @Override
    public final boolean hasMethods(){
        return true;
    };

    @Method(description="Mask passwords with List<String>")
    public Object maskPassword(@Parameter(description="Password name and value tuples") List<String> tuple) throws PasswordListException{

        if(tuple.size() % 2 == 1){
            throw new PasswordListException("Password list tuples must be an even number");
        }

        List<VarPasswordPair> pp = new ArrayList<VarPasswordPair>();

        for (int i = 0; i<tuple.size(); i+=2) {
            pp.add(new VarPasswordPair(tuple.get(i), tuple.get(i+1)));
        }
        return new MaskPasswordsBuildWrapper(pp);
    }

    @Method(description="Mask passwords with array")
    public Object maskPassword(@Parameter(description="Password name and value tuples") String... tuple) throws PasswordListException{
        return maskPassword(Arrays.asList(tuple));
    }

    @Method(description="Mask passwords with closure", closureClass = MaskPasswordClosure.class)
    public Object maskPassword(@Parameter(description="Password name and value tuples") Object closure) throws PasswordListException, DslClosureUnsupported, DslNoClosureClass, IllegalAccessException, InstantiationException{
        MaskPasswordClosure c = (MaskPasswordClosure) runClosure(closure, MaskPasswordClosure.class);
        return maskPassword(c.getTuples());
    }

}

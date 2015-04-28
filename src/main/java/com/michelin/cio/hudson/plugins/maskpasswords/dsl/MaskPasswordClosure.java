package com.michelin.cio.hudson.plugins.maskpasswords.dsl;

import hudson.Extension;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Method;
import org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeremymarshall on 31/01/2015.
 */
@Extension
public class MaskPasswordClosure extends org.jenkinsci.plugins.jobdsl.stub.annotations.dsl.Closure{

    List<String> tuples = new ArrayList<String>();

    @Override
    public String getName(){
        return "mask password closure";
    }

    @Override
    public String getDescription(){
        return "mask password closure";
    }

    @Override
    public final boolean hasMethods(){
        return true;
    };

    @Method(description="add a password value tuple")
    public void entry(@Parameter(description="the password name") String name, @Parameter(description="the password value") String value) {

        this.tuples.add(name);
        this.tuples.add(value);

    }

    public List<String> getTuples(){
        return tuples;
    }
}

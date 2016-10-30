package org.hexu.getcode;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Created by hexu on 16/10/29.
 */
public class GetCodeIdealToolsApplication implements ApplicationComponent {
    public GetCodeIdealToolsApplication() {
    }

    @Override
    public void initComponent() {
        // TODO: insert component initialization logic here
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "GetCodeIdealToolsApplication";
    }
}

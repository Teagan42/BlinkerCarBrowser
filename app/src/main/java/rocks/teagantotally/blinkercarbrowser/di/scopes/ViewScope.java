package rocks.teagantotally.blinkercarbrowser.di.scopes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by tglenn on 9/14/17.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewScope {
}

package quebec.virtualite.utils.ui;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

public class JUnitSuiteUtils
{
    public static class StopOnFirstFailureSuite extends Suite
    {
        public StopOnFirstFailureSuite(Class<?> klass, Class<?>[] suiteClasses)
            throws
            InitializationError
        {
            super(klass, suiteClasses);
        }

        public StopOnFirstFailureSuite(Class<?> klass)
            throws InitializationError
        {
            super(klass, klass.getAnnotation(SuiteClasses.class).value());
        }

        @Override
        public void run(RunNotifier runNotifier)
        {
            runNotifier
                .addListener(new JUnitSuiteUtils.StopOnFirstFailureSuite.FailFastListener
                    (runNotifier));
            super.run(runNotifier);
        }

        private class FailFastListener extends RunListener
        {
            private RunNotifier runNotifier;

            private FailFastListener(RunNotifier runNotifier)
            {
                super();
                this.runNotifier = runNotifier;
            }

            @Override
            public void testFailure(Failure failure) throws Exception
            {
                this.runNotifier.pleaseStop();
            }
        }
    }
}

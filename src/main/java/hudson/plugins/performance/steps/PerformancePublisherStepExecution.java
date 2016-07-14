package hudson.plugins.performance.steps;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.plugins.performance.PerformancePublisher;
import org.jenkinsci.plugins.workflow.steps.AbstractSynchronousNonBlockingStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;

import javax.inject.Inject;

/**
 * Created by booa on 7/13/2016.
 */
public class PerformancePublisherStepExecution extends AbstractSynchronousNonBlockingStepExecution<Void> {
    @StepContextParameter
    private transient TaskListener listener;

    @StepContextParameter
    private transient FilePath ws;

    @StepContextParameter
    private transient Run build;

    @StepContextParameter
    private transient Launcher launcher;

    @Inject
    transient PerformancePublisherStep step;

    @Override
    protected Void run() throws Exception {
        listener.getLogger().println("Running Gatling archiver step.");

        PerformancePublisher publisher = new PerformancePublisher(
                step.getErrorFailedThreshold(),
                step.getErrorUnstableThreshold(),
                step.getErrorUnstableResponseTimeThreshold(),
                step.getRelativeFailedThresholdPositive(),
                step.getRelativeFailedThresholdNegative(),
                step.getRelativeUnstableThresholdPositive(),
                step.getRelativeUnstableThresholdNegative(),
                step.getNthBuildNumber(),
                step.isModePerformancePerTestCase(),
                step.getComparisonType(),
                step.isModeOfThreshold(),
                step.isFailBuildIfNoResultFile(),
                step.isCompareBuildPrevious(),
                step.getParsers(),
                step.isModeThroughput());

        publisher.perform(build, ws, launcher, listener);

        return null;
    }
}

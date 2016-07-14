package hudson.plugins.performance.steps;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.plugins.performance.PerformancePublisher;
import hudson.plugins.performance.PerformanceReportParser;
import hudson.plugins.performance.PerformanceReportParserDescriptor;
import hudson.util.ListBoxModel;
import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by booa on 7/13/2016.
 */
public class PerformancePublisherStep extends AbstractStepImpl{
    private  int errorFailedThreshold;
    private  int errorUnstableThreshold;
    private  String errorUnstableResponseTimeThreshold;
    private  double relativeFailedThresholdPositive;
    private  double relativeFailedThresholdNegative;
    private  double relativeUnstableThresholdPositive;
    private  double relativeUnstableThresholdNegative;
    private  int nthBuildNumber;
    private  boolean modePerformancePerTestCase;
    private  String comparisonType;
    private  boolean modeOfThreshold;
    private  boolean failBuildIfNoResultFile;
    private  boolean compareBuildPrevious;
    private  List<? extends PerformanceReportParser> parsers = new ArrayList<PerformanceReportParser>();
    private  boolean modeThroughput;

    @DataBoundConstructor
    public PerformancePublisherStep(int errorFailedThreshold,
                                int errorUnstableThreshold,
                                String errorUnstableResponseTimeThreshold,
                                double relativeFailedThresholdPositive,
                                double relativeFailedThresholdNegative,
                                double relativeUnstableThresholdPositive,
                                double relativeUnstableThresholdNegative,
                                int nthBuildNumber,
                                boolean modePerformancePerTestCase,
                                String comparisonType,
                                boolean modeOfThreshold,
                                boolean failBuildIfNoResultFile,
                                boolean compareBuildPrevious,
                                List<? extends PerformanceReportParser> parsers,
                                boolean modeThroughput) {
        this.errorFailedThreshold = errorFailedThreshold;
        this.errorUnstableThreshold = errorUnstableThreshold;
        this.errorUnstableResponseTimeThreshold = errorUnstableResponseTimeThreshold;
        this.relativeFailedThresholdPositive = relativeFailedThresholdPositive;
        this.relativeFailedThresholdNegative = relativeFailedThresholdNegative;
        this.relativeUnstableThresholdPositive = relativeUnstableThresholdPositive;
        this.relativeUnstableThresholdNegative = relativeUnstableThresholdNegative;
        this.nthBuildNumber = nthBuildNumber;
        this.modePerformancePerTestCase = modePerformancePerTestCase;
        this.comparisonType = comparisonType;
        this.modeOfThreshold = modeOfThreshold;
        this.failBuildIfNoResultFile = failBuildIfNoResultFile;
        this.compareBuildPrevious = compareBuildPrevious;
        this.parsers = parsers;
        this.modeThroughput = modeThroughput;
    }

    public int getErrorFailedThreshold() {
        return errorFailedThreshold;
    }

    public int getErrorUnstableThreshold() {
        return errorUnstableThreshold;
    }

    public String getErrorUnstableResponseTimeThreshold() {
        return errorUnstableResponseTimeThreshold;
    }

    public double getRelativeFailedThresholdPositive() {
        return relativeFailedThresholdPositive;
    }

    public double getRelativeFailedThresholdNegative() {
        return relativeFailedThresholdNegative;
    }

    public double getRelativeUnstableThresholdPositive() {
        return relativeUnstableThresholdPositive;
    }

    public double getRelativeUnstableThresholdNegative() {
        return relativeUnstableThresholdNegative;
    }

    public int getNthBuildNumber() {
        return nthBuildNumber;
    }

    public boolean isModePerformancePerTestCase() {
        return modePerformancePerTestCase;
    }

    public String getComparisonType() {
        return comparisonType;
    }

    public boolean isModeOfThreshold() {
        return modeOfThreshold;
    }

    public boolean isFailBuildIfNoResultFile() {
        return failBuildIfNoResultFile;
    }

    public boolean isCompareBuildPrevious() {
        return compareBuildPrevious;
    }

    public List<? extends PerformanceReportParser> getParsers() {
        return parsers;
    }

    public boolean isModeThroughput() {
        return modeThroughput;
    }

    @Extension
    public static class DescriptorImpl extends AbstractStepDescriptorImpl {
        public DescriptorImpl() { super(PerformancePublisherStepExecution.class); }

        @Override
        public String getFunctionName() {
            return "performancePublisher";
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return "Publishes JMeter Reports";
        }

        @Override
        public String getHelpFile() {
            return "/plugin/performance/help.html";
        }

        public List<PerformanceReportParserDescriptor> getParserDescriptors() {
            return PerformanceReportParserDescriptor.all();
        }

        /**
         * Populate the comparison type dynamically based on the user selection from
         * the previous time
         *
         * @return the name of the option selected in the previous run
         */
        public ListBoxModel doFillComparisonTypeItems() {
            ListBoxModel items = new ListBoxModel();

            //getting the user selected value
            String temp = PerformancePublisher.getOptionType();

            if (temp.equalsIgnoreCase("ART")) {

                items.add("Average Response Time", "ART");
                items.add("Median Response Time", "MRT");
                items.add("Percentile Response Time", "PRT");
            } else if (temp.equalsIgnoreCase("MRT")) {

                items.add("Median Response Time", "MRT");
                items.add("Percentile Response Time", "PRT");
                items.add("Average Response Time", "ART");
            } else if (temp.equalsIgnoreCase("PRT")) {

                items.add("Percentile Response Time", "PRT");
                items.add("Average Response Time", "ART");
                items.add("Median Response Time", "MRT");
            }

            return items;
        }
    }
}

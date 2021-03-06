/*
 * Copyright 2018, OpenCensus Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.opencensus.examples.gauges;

import io.opencensus.common.ToDoubleFunction;
import io.opencensus.metrics.DerivedDoubleGauge;
import io.opencensus.metrics.LabelKey;
import io.opencensus.metrics.LabelValue;
import io.opencensus.metrics.MetricRegistry;
import io.opencensus.metrics.Metrics;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/** Example showing how to create a {@link DerivedDoubleGauge}. */
public class DerivedDoubleGaugeQuickstart {
  private static final MetricRegistry metricRegistry = Metrics.getMetricRegistry();

  // The label keys and values are used to uniquely identify timeseries.
  private static final List<LabelKey> labelKeys =
      Collections.singletonList(LabelKey.create("Name", "desc"));
  private static final List<LabelValue> labelValues =
      Collections.singletonList(LabelValue.create("Inbound"));

  private static final DerivedDoubleGauge derivedDoubleGauge =
      metricRegistry.addDerivedDoubleGauge("queue_size", "Pending jobs", "1", labelKeys);
  private static final LinkedBlockingQueue blockingQueue = new LinkedBlockingQueue();

  // To instrument a queue's depth.
  private static void doWork() {
    derivedDoubleGauge.createTimeSeries(
        labelValues,
        blockingQueue,
        new ToDoubleFunction<LinkedBlockingQueue>() {
          @Override
          public double applyAsDouble(LinkedBlockingQueue queue) {
            return queue.size();
          }
        });

    // Your code here.
  }

  /** Main launcher for the DerivedDoubleGaugeQuickstart. */
  public static void main(String[] args) {
    // Derived Double Gauge metric is used to report instantaneous measurement of a double value.
    // This is more convenient form when you want to define a gauge by executing a
    // {@link ToDoubleFunction} on an object.

    doWork();
  }
}

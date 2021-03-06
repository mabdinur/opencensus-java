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

package io.opencensus.exporter.trace.jaeger;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

import io.opencensus.trace.export.SpanExporter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class JaegerTraceExporterTest {
  @Mock private SpanExporter spanExporter;

  @Mock private SpanExporter.Handler handler;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void registerUnregisterJaegerExporter() {
    JaegerTraceExporter.register(spanExporter, handler);
    verify(spanExporter)
        .registerHandler(
            eq("io.opencensus.exporter.trace.jaeger.JaegerTraceExporter"), same(handler));
    JaegerTraceExporter.unregister(spanExporter);
    verify(spanExporter)
        .unregisterHandler(eq("io.opencensus.exporter.trace.jaeger.JaegerTraceExporter"));
  }
}

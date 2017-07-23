package com.britebill.interview.statistics.writers;

import com.britebill.interview.statistics.beans.Statistics;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;

public class JsonStatisticsWriter implements StatisticsWriter {

    private static final Log log = LogFactory.getLog(JsonStatisticsWriter.class);

    public void write(Statistics statistics, File file) {
        try {
            JaxbAnnotationModule module = new JaxbAnnotationModule();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(module);
            objectMapper.writeValue(file, statistics);
        } catch (JsonGenerationException e) {
            log.error("Can't generate json:,{}", e);
        } catch (JsonMappingException e) {
            log.error("Can't map json:,{}", e);
        } catch (IOException e) {
            log.error("Can't reach file:,{}", e);
        }
    }
}

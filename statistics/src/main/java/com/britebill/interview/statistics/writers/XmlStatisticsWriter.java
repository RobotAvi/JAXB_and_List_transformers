package com.britebill.interview.statistics.writers;

import com.britebill.interview.statistics.beans.Statistics;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;


public class XmlStatisticsWriter implements StatisticsWriter {

    private static final Log log = LogFactory.getLog(XmlStatisticsWriter.class);

    public void write(Statistics statistics, File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(Statistics.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(statistics, file);
        } catch (JAXBException exception) {

            log.error("Can't write statistic to XML", exception);
        }
    }
}

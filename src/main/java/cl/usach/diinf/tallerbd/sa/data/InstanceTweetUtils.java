package cl.usach.diinf.tallerbd.sa.data;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

public class InstanceTweetUtils {
	
	private static final Logger logger =  Logger.getLogger(InstanceTweetUtils.class.getName());
	
	//Hiding constructor
	private InstanceTweetUtils(){}

	
	public static Instances toWekaInstances(List<InstanceTweet> instancesTweets, Set<String> attributes){
		
		FastVector attrsWeka = new FastVector();
		
		for (String attr : attributes){
			Attribute attribute = new Attribute(attr);
			attrsWeka.addElement(attribute);
		}

		FastVector classAttr = new FastVector();
		
		classAttr.addElement(TweetLabel.POSITIVO.name());
		classAttr.addElement(TweetLabel.NEGATIVO.name());
		
		Attribute classAttribute =  new  Attribute("@@class@@", classAttr);
		
		attrsWeka.addElement(classAttribute);
		
		Instances data = new Instances("Instances", attrsWeka,0);
		
		data.setClassIndex(data.numAttributes()-1);
		for (InstanceTweet instanceTweet : instancesTweets) {
			Instance insWeka = new weka.core.Instance(
					attrsWeka.size());
			insWeka.setDataset(data);
			for (Object obj : attrsWeka.toArray()) {
				Attribute attr = (Attribute)obj;
				if (attr.name().equals("@@class@@")) 
					continue;
				if (instanceTweet.getFeature(attr.name()) == null)
					insWeka.setValue(attr, 0);
				else
					insWeka.setValue(attr, instanceTweet.getFeature(attr.name()));
			}
			insWeka.setValue(classAttribute, instanceTweet.getLabel().name());
			data.add(insWeka);
			logger.info(instanceTweet.toString() + " procesado");

		}
		return data;
		
	}
	
	
}
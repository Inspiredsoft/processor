/*******************************************************************************
* Inspired Processor is a framework to implement a processor manager.
* Copyright (C) 2017 Inspired Soft
* 
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.    
*******************************************************************************/

package it.inspired.wf.spring;

import it.inspired.wf.Activity;
import it.inspired.wf.Processor;
import it.inspired.wf.impl.ConcurrentProcessor;
import it.inspired.wf.impl.DelegateActivity;
import it.inspired.wf.impl.SequenceProcessor;
import it.inspired.wf.impl.WorkflowDefinition;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Spring parser for the xml
 * 
 * @author Massimo Romano
 *
 */
public class WorkflowBeanDefinitionParser extends AbstractBeanDefinitionParser {
	
	private static final String EXCEPTION_HANDLER_ATTRIBUTE = "exceptionHandler";
	private static final String ID_ATTRIBUTE 				= "id";
	private static final String NAME_ATTRIBUTE 				= "name";
	private static final String CLASS_ATTRIBUTE 			= "class";
	private static final String METHOD_ATTRIBUTE 			= "method";
	private static final String RESULT_ATTRIBUTE 			= "result";
	private static final String SEQUENCE_TAG 				= "sequence";
	private static final String CONCURRENT_TAG 				= "concurrent";
	private static final String ACTIVITY_TAG 				= "activity";
	private static final String PARAMER_TAG 				= "parameter";

	@Override
	protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
		try {
			return parseWorkflowElement(element);
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}

	/**
	 * Parse a workflow definition
	 * @param element The element to parse
	 * @return 
	 * @throws Exception
	 */
	private AbstractBeanDefinition parseWorkflowElement(Element element) throws Exception {
		BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition( WorkflowDefinition.class );
		
		parseBaseAttributes( element, factory );
		
		if ( element.getChildNodes().getLength() > 0 ) {
			NodeList nodes = element.getChildNodes();
			
			ManagedList<BeanDefinition> children = new ManagedList<BeanDefinition>( nodes.getLength() );
			
			// Parses the child elements Processor
			Node node = null;
			for ( int i = 0; i < nodes.getLength(); i++ ) {
				node = element.getChildNodes().item( i );
				
				if ( node.getLocalName() != null ) {
					if ( node.getLocalName().equalsIgnoreCase( SEQUENCE_TAG ) ) {
						children.add( parseProcessorElement( (Element) node, SequenceProcessor.class ) );
						
					} else if ( node.getLocalName().equalsIgnoreCase( CONCURRENT_TAG ) ) {
						children.add( parseProcessorElement( (Element) node, ConcurrentProcessor.class ) );
					} 
				}
			}
			
			factory.addPropertyValue( "processors", children );
		}
		
		return factory.getBeanDefinition();
	}
	
	/**
	 * Parse the sequence element
	 * @param element Element to parse
	 * @param clazz Processor class to create
	 * @return Sequence object
	 * @throws Exception
	 */
	private AbstractBeanDefinition parseProcessorElement(Element element, Class<? extends Processor> clazz) throws Exception {
		BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition( clazz );
		
		String id = parseBaseAttributes( element, factory );
		
		if ( element.getChildNodes().getLength() > 0 ) {
			NodeList nodes = element.getChildNodes();
		
			ManagedList<BeanDefinition> children = new ManagedList<BeanDefinition>( nodes.getLength() );
			
			// Parses the child elements Activity
			Node node = null;
			for ( int i = 0; i < nodes.getLength(); i++ ) {
				node = element.getChildNodes().item( i );
				if ( node.getLocalName() != null && node.getLocalName().equalsIgnoreCase( ACTIVITY_TAG ) ) {					
					
					if ( implementsActivity( (Element) node ) ) {
						children.add( parseActivityElement( (Element) node ) );
					
					} else {
						children.add( parseDelegateActivityElement( (Element) node ) );
					}
				}
			}
			
			factory.addPropertyValue( "activities", children );
		} else {
			throw new RuntimeException( "No activities difined for processor id " + id );
		}
		
		return factory.getBeanDefinition();	
	}
	
	/**
	 * Check if the activity implements Activity interface 
	 * @param element The element to check
	 * @return TRUE if the activity implements Activity interface 
	 * @throws Exception
	 */
	private boolean implementsActivity( Element element ) throws Exception {
		/* Create activity bean class */
		Class<?> activity = null;
		String clazz = element.getAttribute( CLASS_ATTRIBUTE );
		if ( clazz != null && !clazz.isEmpty() ) {
			activity =  Class.forName( clazz );
			
			return Activity.class.isAssignableFrom( activity );
			
		} else {
			throw new RuntimeException( "Class must be defined for the activity" );
		}
	}
	
	/**
	 * Parse a base activity
	 * @param element Element to parse
	 * @return
	 * @throws Exception
	 */
	private AbstractBeanDefinition parseActivityElement(Element element) throws Exception {
		
		String clazz = element.getAttribute( CLASS_ATTRIBUTE );
		if ( clazz == null || clazz.isEmpty() ) {
			throw new RuntimeException( "Class must be defined for the activity" );
		}
		
		BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition( clazz );
	
		return factory.getBeanDefinition();
	}
	
	/**
	 * Parse a delegate activity
	 * @param element
	 * @return
	 * @throws Exception
	 */
	private AbstractBeanDefinition parseDelegateActivityElement(Element element) throws Exception {
		BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition( DelegateActivity.class );
		
		String clazz = element.getAttribute( CLASS_ATTRIBUTE );
		if ( clazz == null || clazz.isEmpty() ) {
			throw new RuntimeException( "Class must be defined for the activity" );
		}
		
		factory.addPropertyValue( "delegate", Class.forName( clazz ).newInstance() );
		
		String method = element.getAttribute( METHOD_ATTRIBUTE );
		factory.addPropertyValue( "method", method );
		
		String result = element.getAttribute( RESULT_ATTRIBUTE );
		factory.addPropertyValue( "resultParameter", result );
		
		if ( element.hasAttribute( EXCEPTION_HANDLER_ATTRIBUTE  ) ) {
			String exceptionHandler = element.getAttribute( EXCEPTION_HANDLER_ATTRIBUTE );
			if ( exceptionHandler != null && !exceptionHandler.isEmpty()  ) {
				factory.addPropertyValue( "exceptionHandler", Class.forName( exceptionHandler ).newInstance() );
			}
		}
		
		if ( element.getChildNodes().getLength() > 0 ) {
			NodeList nodes = element.getChildNodes();
			
			List<String> parameters = new ArrayList<String>();
			
			/* Parse parameter */
			Node node = null;
			Element parameter = null;
			for ( int i = 0; i < nodes.getLength(); i++ ) {
				node = element.getChildNodes().item( i );
				if ( node.getLocalName() != null && node.getLocalName().equalsIgnoreCase( PARAMER_TAG ) ) {
					parameter = (Element)node;
					if ( parameter.hasAttribute( NAME_ATTRIBUTE ) ) {
						parameters.add( parameter.getAttribute( NAME_ATTRIBUTE ) );
					} else {
						throw new RuntimeException( "Parameter must have a name attribute" );
					}
				}
			}
			
			factory.addPropertyValue( "parameters", parameters );
		}
		
		return factory.getBeanDefinition();
	}
	
	/**
	 * Parses the default attributes id, name and exceptionHandler
	 * 
	 * @param element Element to parse
	 * @param factory Factory for the element to parse
	 * @throws Exception
	 */
	private String parseBaseAttributes( Element element, BeanDefinitionBuilder factory ) throws Exception {
		String id = element.getAttribute( ID_ATTRIBUTE );
		factory.addPropertyValue( ID_ATTRIBUTE, id);
		
		String name = element.getAttribute( NAME_ATTRIBUTE );
		factory.addPropertyValue( NAME_ATTRIBUTE, name);
		
		if ( element.hasAttribute( EXCEPTION_HANDLER_ATTRIBUTE  ) ) {
			String exceptionHandler = element.getAttribute( EXCEPTION_HANDLER_ATTRIBUTE );
			if ( exceptionHandler != null && !exceptionHandler.isEmpty()  ) {
				factory.addPropertyValue( "defaultExceptionHandler", Class.forName( exceptionHandler ).newInstance() );
			}
		}
		
		return id;
	}
}

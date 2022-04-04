/*
 * myCBR License 3.0
 * 
 * Copyright (c) 2006-2015, by German Research Center for Artificial Intelligence (DFKI GmbH), Germany
 * 
 * Project Website: http://www.mycbr-project.net/
 * 
 * This library is free software; you can redistribute it and/or modify 
 * it under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 3 of the License, or 
 * (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License 
 * along with this library; if not, write to the Free Software Foundation, Inc., 
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 * 
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.
 * 
 * endOfLic */

package de.dfki.mycbr.core.similarity;

import java.util.Iterator;
import java.util.Map;
import java.util.Observable;
import java.util.TreeMap;

import de.dfki.mycbr.core.Project;
import de.dfki.mycbr.core.casebase.Attribute;
import de.dfki.mycbr.core.casebase.IntegerAttribute;
import de.dfki.mycbr.core.casebase.MultipleAttribute;
import de.dfki.mycbr.core.casebase.SimpleAttribute;
import de.dfki.mycbr.core.casebase.SpecialAttribute;
import de.dfki.mycbr.core.model.AttributeDesc;
import de.dfki.mycbr.core.model.IntegerDesc;
import de.dfki.mycbr.core.similarity.config.DistanceConfig;

/**
 * Used for calculating similarities of IntegerAttribute objects.
 * This function can also be used for calculating similarity of 
 * ordered symbol attributes. The basis of this function is a simple
 * graph. You can add additional points to this graph. The similarity function
 * is then the interpolation of these points.
 * 
 * @author myCBR Team
 *
 */
public class AdvancedIntegerFct extends NumberFct {

	private TreeMap<Double, Similarity> points;
	// the first three points consist of the maximal negative distance, 0 and the 
	// maximal positive distance
	private Similarity minPoint;
	private Similarity zeroPoint;
	private Similarity maxPoint;
	private IntegerDesc desc;
	
	/**
	 * Initializes this with the given description. This function can
	 * only be applied to attributes with this description.
	 * The points used for interpolating this function are initialized with standard values.
	 * 
	 * @param desc the description of the attributes to be compared
	 */
	public AdvancedIntegerFct(Project prj, IntegerDesc desc, String name) {
		super(prj,desc,name);
		this.prj = prj;
		this.name = name;
		this.desc = desc;
		points = new TreeMap<Double, Similarity>();
		minPoint = Similarity.get(0.00);
		zeroPoint = Similarity.get(1.00);
		maxPoint = Similarity.get(0.00);
		max = desc.getMax();
		min = desc.getMin();
		diff = max - min;
		
		points.put(-diff, minPoint);
		points.put(0d, zeroPoint);
		points.put(diff, maxPoint);
		
	}

	/**
	 * Initializes this with the given description. This function can
	 * only be applied to attributes with this description.
	 * 
	 * @param desc the description of the attributes to be compared
	 */
	AdvancedIntegerFct(Project prj, IntegerDesc desc, Similarity minSim, Similarity zero, Similarity maxSim, String name) {
		super(prj,desc,name);
		this.prj = prj;
		this.desc = desc;
		this.name = name;
		points = new TreeMap<Double, Similarity>();
		minPoint = minSim;
		zeroPoint = zero;
		maxPoint = maxSim;
		max = desc.getMax();
		min = desc.getMin();
		diff = max - min;
		points.put(-diff, minPoint);
		points.put(0d, zeroPoint);
		points.put(diff, maxPoint);
	}
	
	/**
	 * Calculates the similarity of the specified attributes
	 * using the interpolated function. If the attributes do not 
	 * have the given description, an invalid similarity is returned.
	 * 
	 * @return the similarity of the given attributes 
	 *  	or an invalid similarity if an error occurs 
	 * @throws Exception 
	 */
	@Override
	public Similarity calculateSimilarity(Attribute a1, Attribute a2) throws Exception {
		Similarity res = Similarity.INVALID_SIM;
		if (a1 instanceof SpecialAttribute || a2 instanceof SpecialAttribute) {
			res = prj.calculateSpecialSimilarity(a1, a2);
		} else if (a1 instanceof MultipleAttribute<?> && a1 instanceof MultipleAttribute<?>) {
			res = prj
			.calculateMultipleAttributeSimilarity(this,((MultipleAttribute<?>)a1), (MultipleAttribute<?>)a2);
		} else if ((a1 instanceof SimpleAttribute) && (a2 instanceof SimpleAttribute)) {
			SimpleAttribute value1 = (SimpleAttribute)a1;
			SimpleAttribute value2 = (SimpleAttribute)a2;
			
			if (value1.getAttributeDesc()!=desc || value2.getAttributeDesc() != desc) {
				return res;
			} 
			
			 if (value1 instanceof IntegerAttribute && value2 instanceof IntegerAttribute) {
				int q = ((IntegerAttribute)value1).getValue();
				int c = ((IntegerAttribute)value2).getValue();
				
				res = calculateSimilarity(q, c);
			}
		}
		return res;
	}

	public Similarity calculateSimilarity(int q, int c) throws Exception {

		Similarity res;
		double d = distanceFunction == DistanceConfig.DIFFERENCE ? c-q : c/q;
		
		// check whether the distance has a special point
		Similarity sim = points.get(d);
		if (sim != null) {
			res = sim;
			return res;
		}
		
		// now it has to be located between two points
		// we find them and interpolate a linear function
		// to get the correct similarity for d
		Map.Entry<Double,Similarity> leftPoint = null;
		Map.Entry<Double,Similarity> rightPoint = null;

		Iterator<Map.Entry<Double,Similarity>> it = points.entrySet().iterator();
		do {
			rightPoint = it.next();
			if (rightPoint.getKey() > d) {
				break;
			}
			leftPoint = rightPoint;
		} while (it.hasNext());
		
		if (leftPoint==null) {
			leftPoint = rightPoint;
		}
		
		double leftSim = leftPoint.getValue().getValue();
		double rightSim = rightPoint.getValue().getValue();
		
		// now compute the similarity of the given distance
		// by calculating the value of the line given by the two
		// points: y = (y_2-y_1)/(x_2-x_1)*(x-x_1)+y_1
		// where x is our current distance and y 
		// the similarity we are looking for
		double x1 = leftPoint.getKey();
		double x2 = rightPoint.getKey();
		double y1 = leftSim;
		double y2 = rightSim;
		
		double result = (y2-y1)/(x2-x1)*d + (x2*y1 - x1*y2)/(x2-x1);
		return Similarity.get(result);	
	}
	
	/**
	 * Returns the map of additional point used to interpolate this function
	 * Points are pairs of a double value and a similarity
	 * @return the map of additional points
	 */
	public TreeMap<Double, Similarity> getAdditionalPoints() {
		return points;
	}
	
	/**
	 * Adds the specified point to the list of additional points.
	 * @param key Double, the distance describing the first coordinate of the new point
	 * @param value Similarity used as the value for the second coordinate
	 */
	public void addAdditionalPoint(Double key, Similarity value) {
		points.put(key, value);
		setChanged();
		notifyObservers();
	}

	public double getMin() {
		return min;
	}
	
	public double getMax() {
		return max;
	}
	
	public double getDiff() {
		return diff;
	}
	
	public void setDistanceFct(DistanceConfig df) {
		if (this.distanceFunction == df) {
			return;
		}
		if (df.equals(DistanceConfig.QUOTIENT)) {
			if (min <= 0 && max >= 0) {
				return; // cannot use quotient, when 0 is included in range
			}
		}
		this.distanceFunction = df;
		points.clear(); // can we save points?
		setChanged();
		notifyObservers();
	}
	
	/* (non-Javadoc)
	 * @see de.dfki.mycbr.core.similarity.ISimFct#clone(de.dfki.mycbr.core.model.AttributeDesc, boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void clone(AttributeDesc descNEW, boolean active) {
		if (descNEW instanceof IntegerDesc && !name.equals(Project.DEFAULT_FCT_NAME)) {
			AdvancedIntegerFct f = ((IntegerDesc)descNEW).addAdvancedIntegerFct(name, active);
			f.distanceFunction = this.distanceFunction;
			f.points = (TreeMap<Double, Similarity>) this.points.clone();
			f.isSymmetric = this.isSymmetric;
			f.zeroPoint = this.zeroPoint;
		}
	}	

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		
		if (arg0.equals(desc)) {
			super.min = desc.getMin();
			super.max = desc.getMax();
			super.diff = max-min;
			updatePoints();
		}
		
	}

	/**
	 * 
	 */
	private void updatePoints() {
		DistanceConfig df = this.distanceFunction; // TODO value or reference
		setDistanceFct(DistanceConfig.DIFFERENCE); 
		TreeMap<Double, Similarity> newPoints = new TreeMap<Double, Similarity>(); 
		for (Map.Entry<Double, Similarity> p: points.entrySet()) {
			if (p.getKey()<=diff && p.getKey()>=-diff) { // only copy valid points
				newPoints.put(p.getKey(), p.getValue());
			}
		}
		points = newPoints;
		setDistanceFct(df);
	}
	
}

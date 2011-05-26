package org.anddev.andengine.extension.svg.util;

import org.anddev.andengine.extension.svg.adt.SVGPaint;
import org.anddev.andengine.extension.svg.adt.SVGProperties;
import org.anddev.andengine.extension.svg.util.constants.ISVGConstants;

import android.graphics.Canvas;


/**
 * @author Nicolas Gramlich
 * @since 19:55:18 - 25.05.2011
 */
public class SVGCircleParser implements ISVGConstants {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public static void parse(final SVGProperties pSVGProperties, final Canvas pCanvas, final SVGPaint pSVGPaint) {
		final Float centerX = pSVGProperties.getFloatAttribute(ATTRIBUTE_CENTER_X);
		final Float centerY = pSVGProperties.getFloatAttribute(ATTRIBUTE_CENTER_Y);
		final Float radius = pSVGProperties.getFloatAttribute(ATTRIBUTE_RADIUS);
		if (centerX != null && centerY != null && radius != null) {
			if (pSVGPaint.setFill(pSVGProperties)) {
				pSVGPaint.ensureComputedBoundsInclude(centerX - radius, centerY - radius);
				pSVGPaint.ensureComputedBoundsInclude(centerX + radius, centerY + radius);
				pCanvas.drawCircle(centerX, centerY, radius, pSVGPaint.getPaint());
			}
			if (pSVGPaint.setStroke(pSVGProperties)) {
				// TODO are we missing a this.ensureComputedBoundsInclude(...); here?
				pCanvas.drawCircle(centerX, centerY, radius, pSVGPaint.getPaint());
			}
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}

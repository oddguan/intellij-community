/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package com.intellij.util.xml.impl;

import com.intellij.util.xml.DomAttributeValue;
import com.intellij.util.xml.DomElement;
import com.intellij.util.xml.DomNameStrategy;
import com.intellij.util.xml.reflect.DomAttributeChildDescription;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @author peter
 */
public class AttributeChildDescriptionImpl extends DomChildDescriptionImpl implements DomAttributeChildDescription {
  private final Method myGetterMethod;

  protected AttributeChildDescriptionImpl(final String attributeName, final Method getter) {
    super(attributeName, getter.getGenericReturnType());
    myGetterMethod = getter;
  }

  public final Method getGetterMethod() {
    return myGetterMethod;
  }

  public List<? extends DomElement> getValues(DomElement parent) {
    return Arrays.asList(getDomAttributeValue(parent));
  }

  public String getCommonPresentableName(DomNameStrategy strategy) {
    throw new UnsupportedOperationException("Method getCommonPresentableName is not yet implemented in " + getClass().getName());
  }

  public DomAttributeValue getDomAttributeValue(DomElement parent) {
    try {
      return (DomAttributeValue)myGetterMethod.invoke(parent);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;

    final AttributeChildDescriptionImpl that = (AttributeChildDescriptionImpl)o;

    if (myGetterMethod != null ? !myGetterMethod.equals(that.myGetterMethod) : that.myGetterMethod != null) return false;

    return true;
  }

  public int hashCode() {
    int result = super.hashCode();
    result = 29 * result + (myGetterMethod != null ? myGetterMethod.hashCode() : 0);
    return result;
  }
}

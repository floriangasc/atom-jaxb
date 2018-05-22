package fr.vidal.oss.jaxb.atom.core;

import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import static fr.vidal.oss.jaxb.atom.core.Preconditions.checkState;
import static java.util.Collections.singleton;
import static java.util.Collections.unmodifiableCollection;

/**
 * Definition of a structured extension element.
 * ADD LINKS TO THE DOC
 */
@XmlType
public class StructuredElement implements AdditionalElement {

    private Namespace namespace;
    private String tagName;
    private String value;
    private Collection<Attribute> attributes;
    @XmlAnyElement
    private Collection<AdditionalElement> additionalElements;

    @SuppressWarnings("unused") //jaxb
    public StructuredElement() {
    }

    private StructuredElement(Builder builder) {
        this.namespace = builder.namespace;
        this.tagName = builder.tagName;
        this.value = builder.value;
        this.attributes = builder.attributes;
        this.additionalElements = builder.additionalElements;
    }

    @Override
    public Namespace namespace() {
        return namespace;
    }

    @Override
    public String tagName() {
        return tagName;
    }

    @Override
    public Collection<Attribute> attributes() {
        return unmodifiableCollection(attributes);
    }

    @Override
    public String value() {
        return value;
    }

    public Collection<AdditionalElement> getAdditionalElements() {
        return unmodifiableCollection(additionalElements);
    }

    public static Builder builder(String tagName, AdditionalElement additionalElement) {
        checkState(tagName != null, "TagName is mandatory.");
        checkState(additionalElement != null ,
            "A structured element should contain at least a child element.");

        return new Builder(tagName, additionalElement);
    }

    public static Builder builder(String tagName, Attribute attribute) {
        checkState(tagName != null, "TagName is mandatory.");
        checkState(attribute != null ,
            "A structured element should contain at least an attribute.");

        return new Builder(tagName, attribute);
    }

    public static class Builder {

        private Namespace namespace;
        private String tagName;
        private String value;
        private Collection<Attribute> attributes;
        private Collection<AdditionalElement> additionalElements;

        private Builder(String tagName, AdditionalElement additionalElement) {
            this(tagName, Collections.<Attribute>emptyList(), singleton(additionalElement));
        }

        private Builder(String tagName, Attribute attribute) {
            this(tagName, singleton(attribute), Collections.<AdditionalElement>emptyList());
        }

        private Builder(String tagName, Collection<Attribute> attributes, Collection<AdditionalElement> additionalElements) {
            this.tagName = tagName;
            this.attributes = new LinkedHashSet<>(attributes);
            this.additionalElements = new LinkedHashSet<>(additionalElements);
        }

        public Builder withNamespace(Namespace namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder withValue(String value) {
            this.value = value;
            return this;
        }

        public Builder addChildElement(AdditionalElement childElement) {
            this.additionalElements.add(childElement);
            return this;
        }

        public Builder addChildElements(List<AdditionalElement> childElements) {
            this.additionalElements.addAll(childElements);
            return this;
        }

        public Builder addAttribute(Attribute attribute) {
            this.attributes.add(attribute);
            return this;
        }

        public Builder addAttributes(Collection<Attribute> attributes) {
            this.attributes.addAll(attributes);
            return this;
        }

        public StructuredElement build() {
            return new StructuredElement(this);
        }
    }
}

package com.example.fragmenttest.Model;

import java.util.List;

public class ItemFormulaire {
    private String type;
    private String id;
    private String var_uid;
    private String name;
    private String label;
    private String colspan;
    private String variable;
    private String placeholder;
    private String format;
    private String datetime;
    private int maxLength;
    private String src;
    private String sql;
    private List<option> options;


    private boolean required;

    public ItemFormulaire() {
        super();
    }

    public List<ItemFormulaire.option> getOptions() {
        return options;
    }

    public void setOptions(List<ItemFormulaire.option> options) {
        this.options = options;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getSrc() {
        return src;
    }

    public String getFormat() {
        return format;
    }

    public String getSql() {
        return sql;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public String getColspan() {
        return colspan;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getType() {
        return type;
    }

    public String getVar_uid() {
        return var_uid;
    }

    public String getVariable() {
        return variable;
    }

    public void setColspan(String colspan) {
        this.colspan = colspan;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setVar_uid(String var_uid) {
        this.var_uid = var_uid;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }


    public class option {
        private String value;
        private String label;
        private String text;

        public option() {
            super();
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
        public String getTexy()
        {return text;}

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}

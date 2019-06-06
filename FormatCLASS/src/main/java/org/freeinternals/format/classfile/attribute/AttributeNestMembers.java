/*
 * AttributeInnerClasses.java    5:20 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.classfile.attribute;

import java.io.IOException;
import org.freeinternals.commonlib.core.PosDataInputStream;
import org.freeinternals.format.FileFormatException;
import org.freeinternals.format.classfile.ClassFile;
import org.freeinternals.format.classfile.JavaSEVersion;
import org.freeinternals.format.classfile.u2;

/**
 *
 * The class for the {@code NestMembers} attribute. The {@code NestMembers}
 * attribute has the following format:
 *
 * <pre>
 *    NestMembers_attribute {
 *        u2 attribute_name_index;
 *        u4 attribute_length;
 *
 *        u2 number_of_classes;
 *        u2 classes[number_of_classes];
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since Java 11
 * @see
 * <a href="https://docs.oracle.com/javase/specs/jvms/se12/html/jvms-4.html#jvms-4.7.29">
 * VM Spec: The NestMembers Attribute
 * </a>
 */
public class AttributeNestMembers extends AttributeInfo {

    /**
     * The value of the number_of_classes item indicates the number of entries
     * in the {@link #classes}table.
     */
    public transient final u2 number_of_classes;

    /**
     * Each value in the classes array must be a valid index into the
     * {@link ClassFile#constant_pool} table. The
     * {@link ClassFile#constant_pool} entry at that index must be a
     * {@link org.freeinternals.format.classfile.constant.ConstantClassInfo} structure representing a class or interface
     * which is a member of the nest hosted by the current class or interface.
     */
    public transient final u2[] classes;

    AttributeNestMembers(final u2 nameIndex, final String type, final PosDataInputStream posDataInputStream) throws IOException, FileFormatException {
        super(nameIndex, type, posDataInputStream, ClassFile.Version.Format_55_0, JavaSEVersion.Version_11);

        this.number_of_classes = new u2(posDataInputStream);
        if (this.number_of_classes.value > 0) {
            this.classes = new u2[this.number_of_classes.value];
            for (int i = 0; i < this.number_of_classes.value; i++) {
                this.classes[i] = new u2(posDataInputStream);
            }
        } else {
            this.classes = null;
        }

        super.checkSize(posDataInputStream.getPos());
    }
}
/*
 * ConstantFloatInfo.java    4:41 AM, August 5, 2007
 *
 * Copyright  2007, FreeInternals.org. All rights reserved.
 * Use is subject to license terms.
 */
package org.freeinternals.format.classfile.constant;

import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;
import org.freeinternals.commonlib.core.BytesTool;
import org.freeinternals.commonlib.core.PosDataInputStream;
import org.freeinternals.commonlib.ui.JTreeNodeFileComponent;
import org.freeinternals.format.classfile.ClassFile;
import org.freeinternals.format.classfile.JavaSEVersion;

/**
 * The class for the {@code CONSTANT_Float_info} structure in constant pool. The
 * {@code CONSTANT_Float_info} structure has the following format:
 *
 * <pre>
 *    CONSTANT_Float_info {
 *        u1 tag;
 *        u4 bytes;
 *    }
 * </pre>
 *
 * @author Amos Shi
 * @since Java 1.0.2
 * @see
 * <a href="https://docs.oracle.com/javase/specs/jvms/se12/html/jvms-4.html#jvms-4.4.4">
 * VM Spec: The CONSTANT_Float_info Structure
 * </a>
 */
public class ConstantFloatInfo extends CPInfo {

    public static final int RAW_DATA_SIZE = 4;
    public static final int LENGTH = 5;
    public final byte[] rawData;
    public final Float floatValue;

    ConstantFloatInfo(final PosDataInputStream posDataInputStream) throws IOException {
        super(CPInfo.ConstantType.CONSTANT_Float.tag, true, ClassFile.Version.Format_45_3, JavaSEVersion.Version_1_0_2);
        super.startPos = posDataInputStream.getPos() - 1;
        super.length = LENGTH;

        this.rawData = posDataInputStream.getBuf(posDataInputStream.getPos(), RAW_DATA_SIZE);
        this.floatValue = posDataInputStream.readFloat();
    }

    @Override
    public String getName() {
        return ConstantType.CONSTANT_Float.name();
    }

    @Override
    public String getDescription() {
        return String.format("%s: Start Position: [%d], length: [%d], value: [%f].",
                this.getName(), this.startPos, this.length, this.floatValue);
    }
    
    @Override
    public String toString(CPInfo[] constant_pool) {
        return String.valueOf(this.floatValue);
    }

    @Override
    public void generateTreeNode(DefaultMutableTreeNode parentNode, ClassFile classFile) {
        parentNode.add(new DefaultMutableTreeNode(new JTreeNodeFileComponent(
                this.getStartPos() + 1,
                4,
                "bytes: " + this.floatValue + " - " + BytesTool.getByteDataHexView(this.rawData)
        )));
    }
}

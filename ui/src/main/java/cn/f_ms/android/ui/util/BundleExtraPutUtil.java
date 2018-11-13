package cn.f_ms.android.ui.util;

import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class BundleExtraPutUtil {

    private BundleExtraPutUtil() {
        throw new IllegalStateException("no instance");
    }

    /**
     * put extra, wrapper object type
     * <p>
     * throw IlleaglStateException if unaccept value's type
     *
     * @param bundle bundle
     * @param name   bundle-key
     * @param value  bundle-value
     * @return your bundle
     * @throws IllegalStateException throw if unaccept value's type
     */
    public static Bundle putExtra(Bundle bundle, String name, Object value) throws IllegalStateException {

        if (bundle == null) {
            throw new IllegalArgumentException("bundle can't be null");
        }

        if (name == null) {
            throw new IllegalArgumentException("name can't be null");
        }

        if (value == null) {
            return bundle;
        }

        PutExtraMethod finalExtraMethod = null;
        for (PutExtraMethod putExtraMethod : PutExtraMethod.values()) {
            if (putExtraMethod.isMyType(value)) {
                finalExtraMethod = putExtraMethod;
                break;
            }
        }

        if (finalExtraMethod == null) {
            throw new IllegalStateException(String.format(
                    "unaccept bundle extra value type: '%s'", value.getClass().getName()
            ));
        }

        finalExtraMethod.putExtra(bundle, name, value);

        return bundle;
    }

    private enum PutExtraMethod {
        BOOLEAN(Boolean.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putBoolean(name, (Boolean) value);
            }
        },
        BYTE(Byte.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putByte(name, (Byte) value);
            }
        },
        CHAR(Character.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putChar(name, (Character) value);
            }
        },
        SHORT(Short.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putShort(name, (Short) value);
            }
        },
        INT(Integer.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putInt(name, (Integer) value);
            }
        },
        LONG(Long.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putLong(name, (Long) value);
            }
        },
        FLOAT(Float.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putFloat(name, (Float) value);
            }
        },
        DOUBLE(Double.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putDouble(name, (Double) value);
            }
        },
        STRING(String.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putString(name, (String) value);
            }
        },
        CHARSEQUENCE(CharSequence.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putCharSequence(name, (CharSequence) value);
            }
        },
        BOOLEAN_ARRAY(boolean[].class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putBooleanArray(name, (boolean[]) value);
            }
        },
        BYTE_ARRAY(byte[].class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putByteArray(name, (byte[]) value);
            }
        },
        SHORT_ARRAY(short[].class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putShortArray(name, (short[]) value);
            }
        },
        CHAR_ARRAY(char[].class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putCharArray(name, (char[]) value);
            }
        },
        INT_ARRAY(int[].class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putIntArray(name, (int[]) value);
            }
        },
        LONG_ARRAY(long[].class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putLongArray(name, (long[]) value);
            }
        },
        FLOAT_ARRAY(float[].class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putFloatArray(name, (float[]) value);
            }
        },
        DOUBLE_ARRAY(double[].class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putDoubleArray(name, (double[]) value);
            }
        },
        STRING_ARRAY(String[].class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putStringArray(name, (String[]) value);
            }
        },
        CHARSEQUENCE_ARRAY(CharSequence[].class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putCharSequenceArray(name, (CharSequence[]) value);
            }
        },
        SERIALIZABLE(Serializable.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putSerializable(name, (Serializable) value);
            }
        },
        PARCELABLE(Parcelable.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putParcelable(name, (Parcelable) value);
            }
        },
        PARCELABLE_ARRAY(Parcelable[].class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putParcelableArray(name, (Parcelable[]) value);
            }
        },
        BUNDLE(Bundle.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {
                bundle.putBundle(name, (Bundle) value);
            }
        },
        ABOUT_ARRAYLIST(ArrayList.class) {
            @Override
            void putExtra(Bundle bundle, String name, Object value) {

                ArrayList list = (ArrayList) value;

                if (!list.isEmpty()) {
                    Object element = list.get(0);

                    if (element instanceof Parcelable) {
                        bundle.putParcelableArrayList(name, list);
                        return;
                    }

                    if (element instanceof String) {
                        bundle.putStringArrayList(name, list);
                        return;
                    }

                    if (element instanceof CharSequence) {
                        bundle.putCharSequenceArrayList(name, list);
                        return;
                    }

                    if (element instanceof Integer) {
                        bundle.putIntegerArrayList(name, list);
                        return;
                    }

                    return;
                }

                bundle.putIntegerArrayList(name, (ArrayList<Integer>) value);
            }
        };

        private Class acceptTypeClass;

        PutExtraMethod(Class acceptTypeClass) {
            if (acceptTypeClass == null) {
                throw new NullPointerException("acceptTypeClass can't be null");
            }
            this.acceptTypeClass = acceptTypeClass;
        }

        boolean isMyType(Object obj) {
            return acceptTypeClass.isInstance(obj);
        }

        abstract void putExtra(Bundle bundle, String name, Object value);
    }
}


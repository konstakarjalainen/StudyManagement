package fi.tuni.prog3.sisu.entity.sisu;

public class SisuCourse extends SisuModule {

    MinMaxInt credits;

    @Override
    public String getType() {
        return "Course";
    }

    public static class MinMaxInt {
        Integer min;
        Integer max;

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }
    }
}

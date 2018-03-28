package deu.hlju.dawn.studentattendance.bean;

import java.util.List;

public class FaceDetectResult {
    @Override
    public String toString() {
        return "FaceDetectResult{" +
                "image_id='" + image_id + '\'' +
                ", request_id='" + request_id + '\'' +
                ", time_used=" + time_used +
                ", faces=" + faces +
                ", error_message='" + error_message + '\'' +
                '}';
    }

    /**
     * image_id : 2FA+oHQMVlsN9uqqMTXPZA==
     * request_id : 1522046969,2109a886-9436-44aa-8b05-a401b770a301
     * time_used : 224
     * faces : [{"face_rectangle":{"width":166,"top":87,"left":158,"height":166},"face_token":"0bd5b93cc4cce879f2f0994ced5bc4f2"}]
     */

    private String image_id;
    private String request_id;
    private int time_used;
    private List<FacesBean> faces;
    /**
     * error_message : MISSING_ARGUMENTS: image_url, image_file, image_base64
     */

    private String error_message;

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getTime_used() {
        return time_used;
    }

    public void setTime_used(int time_used) {
        this.time_used = time_used;
    }

    public List<FacesBean> getFaces() {
        return faces;
    }

    public void setFaces(List<FacesBean> faces) {
        this.faces = faces;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public static class FacesBean {
        @Override
        public String toString() {
            return "FacesBean{" +
                    "face_rectangle=" + face_rectangle +
                    ", face_token='" + face_token + '\'' +
                    '}';
        }

        /**
         * face_rectangle : {"width":166,"top":87,"left":158,"height":166}
         * face_token : 0bd5b93cc4cce879f2f0994ced5bc4f2
         */

        private FaceRectangleBean face_rectangle;
        private String face_token;

        public FaceRectangleBean getFace_rectangle() {
            return face_rectangle;
        }

        public void setFace_rectangle(FaceRectangleBean face_rectangle) {
            this.face_rectangle = face_rectangle;
        }

        public String getFace_token() {
            return face_token;
        }

        public void setFace_token(String face_token) {
            this.face_token = face_token;
        }

        public static class FaceRectangleBean {
            @Override
            public String toString() {
                return "FaceRectangleBean{" +
                        "width=" + width +
                        ", top=" + top +
                        ", left=" + left +
                        ", height=" + height +
                        '}';
            }

            /**
             * width : 166
             * top : 87
             * left : 158
             * height : 166
             */

            private int width;
            private int top;
            private int left;
            private int height;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getTop() {
                return top;
            }

            public void setTop(int top) {
                this.top = top;
            }

            public int getLeft() {
                return left;
            }

            public void setLeft(int left) {
                this.left = left;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }
        }
    }
}

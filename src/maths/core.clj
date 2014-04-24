;; (ns maths.core
;;   (:require [clojure.math.numeric-tower :as poop])
;;   (:import (com.jogamp.opengl.awt))
;;   )



(import '(java.awt Frame)
        '(java.awt.event WindowListener WindowAdapter KeyListener KeyEvent)
        '(javax.media.opengl GLEventListener GL GL2ES1 GLAutoDrawable)
        '(javax.media.opengl.awt GLCanvas)
        '(javax.media.opengl.glu GLU)
        '(javax.media.opengl.fixedfunc GLMatrixFunc GLLightingFunc)
        '(com.jogamp.opengl.util Animator))
(def rotateT 0)
(def glu (new GLU))
(def canvas (new GLCanvas))
(def frame (new Frame "Jogl 3D Shape/Rotation"))
(def animator (new Animator canvas))
(defn exit "Stops animation and closes the OpenGL frame." []
  (.stop animator)
  (.dispose frame))

(.addGLEventListener
 canvas
 (proxy [GLEventListener] []
   (display
    [#^GLAutoDrawable drawable]
    (doto (.getGL drawable)
      (.glClear (. GL GL_COLOR_BUFFER_BIT))
      (.glClear (. GL GL_DEPTH_BUFFER_BIT))
      (.glLoadIdentity)
      (.glTranslatef 0 0 -5)

      (.glRotatef rotateT 1 0 0)
      (.glRotatef rotateT 0 1 0)
      (.glRotatef rotateT 0 0 1)
      (.glRotatef rotateT 0 1 0)

      (.glBegin (. GL GL_TRIANGLES))

      ; Front
      (.glColor3f 0 1 1)
      (.glVertex3f 0 1 0)
      (.glColor3f 0 0 1)
      (.glVertex3f -1 -1 1)
      (.glColor3f 0 0 0)
      (.glVertex3f 1 -1 1)


      ; Right Side Facing Front
      (.glColor3f 0 1 1)
      (.glVertex3f 0 1 0)
      (.glColor3f 0 0 1)
      (.glVertex3f 1 -1 1)
      (.glColor3f 0 0 0)
      (.glVertex3f 0 -1 -1)

      ; Left Side Facing Front
      (.glColor3f 0 1 1)
      (.glVertex3f 0 1 0)
      (.glColor3f 0 0 1)
      (.glVertex3f 0 -1 -1)
      (.glColor3f 0 0 0)
      (.glVertex3f -1 -1 1)

      ;Bottom
      (.glColor3f 0 0 0)
      (.glVertex3f -1 -1 1)
      (.glColor3f 0.1 0.1 0.1)
      (.glVertex3f 1 -1 1)
      (.glColor3f 0.2 0.2 0.2)
      (.glVertex3f 0 -1 -1)

      (.glEnd))
    (def rotateT (+ 0.2 rotateT)))

   (displayChanged [drawable m d])

   (init
    [#^GLAutoDrawable drawable]
    (doto (.getGL drawable)
      (.glShadeModel (. GLLightingFunc GL_SMOOTH))
      (.glClearColor 0 0 0 0)
      (.glClearDepth 1)
      (.glEnable (. GL GL_DEPTH_TEST))
      (.glDepthFunc (. GL GL_LEQUAL))
      (.glHint (. GL2ES1 GL_PERSPECTIVE_CORRECTION_HINT)
               (. GL GL_NICEST)))
    (.addKeyListener
     drawable
     (proxy [KeyListener] []
       (keyPressed
        [e]
        (when (= (.getKeyCode e) (. KeyEvent VK_ESCAPE))
          (exit))))))

   (reshape
    [#^GLAutoDrawable drawable x y w h]
    (when (> h 0)
      (let [gl (.getGL drawable)]
        (.glMatrixMode gl (. GLMatrixFunc GL_PROJECTION))
        (.glLoadIdentity gl)
        (.gluPerspective glu 50.0 (double (/ w h)) 1.0 1000.0)
        (.glMatrixMode gl (. GLMatrixFunc GL_MODELVIEW))
        (.glLoadIdentity gl))))))

(doto frame
  (.add canvas)
  (.setSize 640 480)
  (.setUndecorated false)
  (.setExtendedState (. Frame NORMAL))
  (.addWindowListener
   (proxy [WindowAdapter] []
     (windowClosing [e] (exit))))
  (.setVisible true))
(.start animator)
(.requestFocus canvas)


 (defn -main
   "I don't do a whole lot ... yet."
   [& args]
   ;; (println "Hello, World!")
   )


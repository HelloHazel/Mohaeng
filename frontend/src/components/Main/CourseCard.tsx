import { useAppDispatch, useAppSelector } from "@/src/hooks/useReduxHooks";
import { IRecommandCourse } from "@/src/interfaces/Course.type";
import { listLikeToggleAction } from "@/src/store/reducers/CourseListSlice";
import { openModal } from "@/src/store/reducers/modalSlice";
import Image from "next/image";
import { useRouter } from "next/router";
import IsLikeState from "../UI/IsLikeState";
import styles from "./CourseCard.module.css";

interface ICourseCardProps {
  recommandCourse: IRecommandCourse[];
  onUpdateCourse: (updatedCourse: IRecommandCourse) => void;
}

const CourseCard = ({
  courseId,
  title,
  content,
  thumbnailUrl,
  likeCount,
  isLiked,
  onUpdateCourse,
}: any) => {
  const handleToggleLike = (
    e: React.MouseEvent<HTMLDivElement, MouseEvent>
  ) => {
    e.stopPropagation();
    if (userId) {
      dispatch(listLikeToggleAction({ courseId, isLiked }));
      // isLiked를 토글하고 likeCount를 1증가 혹은 감소
      onUpdateCourse({
        courseId,
        title,
        content,
        thumbnailUrl,
        isLiked: !isLiked,
        likeCount: isLiked ? likeCount - 1 : likeCount + 1,
      });
    } else {
      dispatch(
        openModal({
          modalType: "LoginModal",
          isOpen: true,
        })
      );
    }
  };

  const router = useRouter();
  const dispatch = useAppDispatch();
  const { id: userId } = useAppSelector((state) => state.token);

  const handleCourseCard = () => {
    router.push(`/course/${courseId}`);
  };

  return (
    <div className={styles["course-card-container"]} onClick={handleCourseCard}>
      <div className={styles["course-image-container"]}>
        <Image
          src={thumbnailUrl}
          alt={title}
          width={700}
          height={700}
          priority
        />
        <IsLikeState
          likeCount={likeCount}
          isLiked={isLiked}
          onClick={handleToggleLike}
        />
      </div>
      <div className={styles["course-card-content"]}>
        <div className={styles["course-card-title"]}>
          <h3>{title}</h3>
        </div>
        <div className={styles["course-card-desc"]}>
          <p>{content}</p>
        </div>
      </div>
    </div>
  );
};

export default CourseCard;

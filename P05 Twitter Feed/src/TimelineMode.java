/**
 * An enum representing the three states of the timeline:
 * - Chronological (in decreasing time order)
 * - Verified only (only tweets from verified users, in decreasing time order)
 * - High engagement (only tweets with a total engagement level over a given threshold, in
 *   decreasing time order)
 */
public enum TimelineMode {
  CHRONOLOGICAL, VERIFIED_ONLY, LIKE_RATIO;
}
